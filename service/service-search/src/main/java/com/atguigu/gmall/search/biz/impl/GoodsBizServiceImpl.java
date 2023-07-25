package com.atguigu.gmall.search.biz.impl;

import cn.hutool.core.util.PageUtil;
import com.atguigu.gmall.search.*;
import com.atguigu.gmall.search.biz.GoodsBizService;
import com.atguigu.gmall.search.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description：GoodsBizService实现类
 * @Author: scv
 * @CreateTime: 2023-07-22  23:18
 * @Version: 1.0
 */
@Service
@Slf4j
public class GoodsBizServiceImpl implements GoodsBizService {
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Override
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }

    @Override
    public void deleteById(Long skuId) {
        goodsRepository.deleteById(skuId);
    }

    @Override
    public SearchResponseVo search(SearchParamDTO searchParamDTO) {
        log.info("GoodsBizServiceImpl----search方法执行了");
        // TODO: 调用esRestTemplate完成高级搜索
        //准备请求参数
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // TODO: 2023/7/23 设置搜索参数
        if (searchParamDTO.getCategory1Id() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("category1Id",searchParamDTO.getCategory1Id()));
        }
        if (searchParamDTO.getCategory2Id() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("category2Id",searchParamDTO.getCategory2Id()));
        }
        if (searchParamDTO.getCategory3Id() != null){
            boolQueryBuilder.must(QueryBuilders.termQuery("category3Id",searchParamDTO.getCategory3Id()));
        }
        //关键字搜索
        String keyword = searchParamDTO.getKeyword();
        if (!StringUtils.isEmpty(keyword)){
            boolQueryBuilder.must(QueryBuilders.termQuery("title",searchParamDTO.getKeyword()));
        }

        //品牌搜索
        String trademark = searchParamDTO.getTrademark();
        if (!StringUtils.isEmpty(trademark)){
            String[] trademarkArr = trademark.split(":");
            long tmId = Long.parseLong(trademarkArr[0]);
            boolQueryBuilder.must(QueryBuilders.termQuery("tmId",tmId));
        }

        //平台属性搜索
        String[] props = searchParamDTO.getProps();
        if (props != null && props.length > 0){
            for (String prop : props) {
                String[] split = prop.split(":");
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId",split[0]));
                boolQuery.must(QueryBuilders.termQuery("attrs.attrValue",split[1]));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
                boolQueryBuilder.must(nestedQuery);
            }
        }

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();

        //设置分页参数
        Pageable pageable = PageRequest.of(searchParamDTO.getPageNo() - 1, searchParamDTO.getPageSize());
        nativeSearchQuery.setPageable(pageable);

        //设置排序参数
        String order = searchParamDTO.getOrder();
        if (!StringUtils.isEmpty(order)){
            String[] split = order.split(":");
            switch (split[0]){
                case "1":
                    nativeSearchQuery.addSort(Sort.by(split[1].equalsIgnoreCase("desc")? Sort.Direction.DESC: Sort.Direction.ASC,"hotScore"));
                    break;
                case "2":
                    nativeSearchQuery.addSort(Sort.by(split[1].equalsIgnoreCase("desc")?Sort.Direction.DESC: Sort.Direction.ASC,"price"));
                    break;
            }
        }
        //添加品牌聚合搜索条件
        TermsAggregationBuilder tmIdAggregation = AggregationBuilders.terms("tmIdAgg").field("tmId").size(100);
        tmIdAggregation.subAggregation(AggregationBuilders.terms("tmNameAgg").field("tmName").size(10));
        tmIdAggregation.subAggregation(AggregationBuilders.terms("tmLogoUrlAgg").field("tmLogoUrl").size(10));
        nativeSearchQuery.addAggregation(tmIdAggregation);

        //添加平台属性聚合搜索条件
        NestedAggregationBuilder nestedAggregationBuilder = AggregationBuilders.nested("attrsAgg","attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attrIdAgg").field("attrs.attrId").size(100);
        attrIdAgg.subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName").size(10));
        attrIdAgg.subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue").size(100));
        nestedAggregationBuilder.subAggregation(attrIdAgg);
        nativeSearchQuery.addAggregation(nestedAggregationBuilder);


        //设置高亮参数

        if (!StringUtils.isEmpty(searchParamDTO.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("title");
            highlightBuilder.preTags("<font color='red'>");
            highlightBuilder.postTags("</font>");
            HighlightQuery highlightQuery = new HighlightQuery(highlightBuilder);
            nativeSearchQuery.setHighlightQuery(highlightQuery);
        }

        SearchHits<Goods> searchHitsList = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class);
        //解析封装响应结果数据
        SearchResponseVo searchResponseVo = parseReponseResult(searchHitsList,searchParamDTO);
        return searchResponseVo;
    }

    @Override
    public void updateHotScore(Long goodsId, Integer hotScore) {
        Optional<Goods> goodsOptional = goodsRepository.findById(goodsId);
        Goods goods = goodsOptional.get();
        goods.setHotScore(Long.parseLong(String.valueOf(hotScore.intValue())));
        goodsRepository.save(goods);
    }

    private SearchResponseVo parseReponseResult(SearchHits<Goods> searchHitsList, SearchParamDTO searchParamDTO) {
        //创建对象
        SearchResponseVo searchResponseVo = new SearchResponseVo();

        searchResponseVo.setSearchParam(searchParamDTO);

        //品牌面包屑

        String trademark = searchParamDTO.getTrademark();
        if (!StringUtils.isEmpty(trademark)){
            String[] split = trademark.split(":");
            searchResponseVo.setTrademarkParam("品牌:"+split[1]);
        }

        //平台属性面包屑
        ArrayList<SearchAttr> searchAttrs = new ArrayList<>();
        String[] props = searchParamDTO.getProps();
        if (props != null && props.length > 0){
            for (String prop : props) {
                String[] split = prop.split(":");
                Long attrId = Long.parseLong(split[0]);
                String attrValueName = split[1];
                String attrName = split[2];
                SearchAttr searchAttr = new SearchAttr();
                searchAttr.setAttrId(attrId);
                searchAttr.setAttrValue(attrValueName);
                searchAttr.setAttrName(attrName);
                searchAttrs.add(searchAttr);
            }
        }

        //设置排序字段

        String order = searchParamDTO.getOrder();
        SearchOrderMapVo searchOrderMapVo = new SearchOrderMapVo("1", "desc");
        if (!StringUtils.isEmpty(order) && order.contains(":")){
            String[] split = order.split(":");
            searchOrderMapVo.setSort(split[1]);
            searchOrderMapVo.setType(split[0]);
        }
        searchResponseVo.setOrderMap(searchOrderMapVo);

        //设置商品列表
        List<SearchHit<Goods>> searchHits = searchHitsList.getSearchHits();
        List<Goods> goodsList = searchHits.stream().map(searchHit -> {
            Goods goods = searchHit.getContent();
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            if (highlightFields!=null && highlightFields.size() > 0){
                List<String> title = highlightFields.get("title");
                if (title != null && title.size() > 0){
                    goods.setTitle(title.get(0));
                }
            }
            return goods;
        }).collect(Collectors.toList());
        searchResponseVo.setGoodsList(goodsList);

        //设置分页参数
        searchResponseVo.setPageNo(searchParamDTO.getPageNo());
        int totalPage = PageUtil.totalPage((int) (searchHitsList.getTotalHits()), searchParamDTO.getPageSize().intValue());
        searchResponseVo.setTotalPages(Long.parseLong(String.valueOf(totalPage)));


        List<SearchTmVo> searchTmVoList = new ArrayList();
        Aggregations aggregations = searchHitsList.getAggregations();
        ParsedLongTerms tmIdAgg = aggregations.get("tmIdAgg");
        List<? extends Terms.Bucket> buckets = tmIdAgg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            long tmId = Long.parseLong(bucket.getKeyAsString());

            //获取子聚合结果
            Aggregations subAggs = bucket.getAggregations();
            ParsedStringTerms tmNameAgg = subAggs.get("tmNameAgg");
            List<? extends Terms.Bucket> tmNameAggBuckets = tmNameAgg.getBuckets();
            String tmName = tmNameAggBuckets.get(0).getKeyAsString();
            ParsedStringTerms tmLogoUrlAgg = subAggs.get("tmLogoUrlAgg");
            List<? extends Terms.Bucket> tmLogoUrlAggBuckets = tmLogoUrlAgg.getBuckets();
            String tmLogoUrl = tmLogoUrlAggBuckets.get(0).getKeyAsString();
            SearchTmVo searchTmVo = new SearchTmVo();
            searchTmVo.setTmId(tmId);
            searchTmVo.setTmName(tmName);
            searchTmVo.setTmLogoUrl(tmLogoUrl);
            searchTmVoList.add(searchTmVo);
        }
        searchResponseVo.setTrademarkList(searchTmVoList);
        searchResponseVo.setUrlParam(getUrlParam(searchParamDTO));

        //解析查询平台属性
        ParsedNested attrsAgg = aggregations.get("attrsAgg");
        Aggregations attrsAggregation = attrsAgg.getAggregations();
        ParsedLongTerms attrIdAgg = attrsAggregation.get("attrIdAgg");
        ArrayList<SearchRespAttrVo> searchRespAttrVoArrayList = new ArrayList<>();

        List<? extends Terms.Bucket> attrIdBuckets = attrIdAgg.getBuckets();
        for (Terms.Bucket bucket : attrIdBuckets) {
            //获取平台属性id
            long attrId = Long.parseLong(bucket.getKeyAsString());

            //获取平台属性名称
            Aggregations bucketAggregations = bucket.getAggregations();
            ParsedStringTerms attrNameAgg = bucketAggregations.get("attrNameAgg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();

            ParsedStringTerms attrValueAgg = bucketAggregations.get("attrValueAgg");
            List<? extends Terms.Bucket> attrValueBuckets = attrValueAgg.getBuckets();
            ArrayList<String> attrValueList = new ArrayList<>();
            for (Terms.Bucket bucket1 : attrValueBuckets) {
                String attrValue = bucket1.getKeyAsString();
                attrValueList.add(attrValue);
            }
            SearchRespAttrVo searchRespAttrVo = new SearchRespAttrVo();
            searchRespAttrVo.setAttrId(attrId);
            searchRespAttrVo.setAttrName(attrName);
            searchRespAttrVo.setAttrValueList(attrValueList);
            searchRespAttrVoArrayList.add(searchRespAttrVo);
        }

        searchResponseVo.setAttrsList(searchRespAttrVoArrayList);

        return searchResponseVo;
    }

    private String getUrlParam(SearchParamDTO searchParamDTO) {
        StringBuilder builder = new StringBuilder("list.html?");

        //拼接
        if (!StringUtils.isEmpty(searchParamDTO.getCategory1Id())){
            builder.append("&category1Id=").append(searchParamDTO.getCategory1Id());
        }
        if (!StringUtils.isEmpty(searchParamDTO.getCategory2Id())){
            builder.append("&category2Id=").append(searchParamDTO.getCategory2Id());
        }
        if (!StringUtils.isEmpty(searchParamDTO.getCategory3Id())){
            builder.append("&category3Id=").append(searchParamDTO.getCategory3Id());
        }

        if (!StringUtils.isEmpty(searchParamDTO.getKeyword())){
            builder.append("&keyword=").append(searchParamDTO.getKeyword());
        }
        //拼接查询条件
        if (!StringUtils.isEmpty(searchParamDTO.getTrademark())){
            builder.append("&trademark=").append(searchParamDTO.getTrademark());
        }

        //拼接平台属性条件
        String[] props = searchParamDTO.getProps();
        if (props != null && props.length > 0){
            for (String prop : props) {
                builder.append("&props=").append(prop);
            }
        }
        return builder.toString();
    }
}
