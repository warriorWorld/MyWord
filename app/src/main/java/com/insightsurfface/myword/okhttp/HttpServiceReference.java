//package com.insightsurfface.myword.okhttp;
//
//import com.store.common.bean.entitiy.DeliverHomeNumBean;
//import com.store.common.bean.entitiy.HistoryLossInfoVo;
//import com.store.common.bean.entitiy.LossDetailVoResult;
//import com.store.common.bean.entitiy.ProductLossCartVo;
//import com.store.common.bean.entitiy.ProductLossInfoVoResult;
//import com.store.common.bean.entitiy.QueryProductByBarcodeResult;
//import com.store.common.bean.result.AbnormalDeliveryResult;
//import com.store.common.bean.result.AddCartResult;
//import com.store.common.bean.result.AddInventoryResult;
//import com.store.common.bean.result.BaseDataResult;
//import com.store.common.bean.result.BaseResult;
//import com.store.common.bean.result.BhOrderDetailsResult;
//import com.store.common.bean.result.CategoryResult;
//import com.store.common.bean.result.CommitProductLossResult;
//import com.store.common.bean.result.CommitSucessResult;
//import com.store.common.bean.result.DeleteProductLossResult;
//import com.store.common.bean.result.FindPurchaseOrderDetailResult;
//import com.store.common.bean.result.FindPurchaseOrderListPageResult;
//import com.store.common.bean.result.FindShippedOrderDetailResult;
//import com.store.common.bean.result.FindShippedOrderTypeResult;
//import com.store.common.bean.result.GetCustomerServiceListTCPResult;
//import com.store.common.bean.result.GetExOrderInfoResult;
//import com.store.common.bean.result.GetExOrderListResult;
//import com.store.common.bean.result.GetGonggaoDetailResult;
//import com.store.common.bean.result.GetInventoryPersonListResult;
//import com.store.common.bean.result.GetPartInfoResult;
//import com.store.common.bean.result.GetSortingOrderDetailResult;
//import com.store.common.bean.result.GetSortingOrderListResult;
//import com.store.common.bean.result.GetUserInfoResult;
//import com.store.common.bean.result.GshopCategoryResult;
//import com.store.common.bean.result.HistoryInventoryInfoVoResult;
//import com.store.common.bean.result.LoginOutResult;
//import com.store.common.bean.result.LoginResult;
//import com.store.common.bean.result.ModifyWareBhProductResult;
//import com.store.common.bean.result.PurchaseStatusCountResult;
//import com.store.common.bean.result.QueryActivityListResult;
//import com.store.common.bean.result.QueryBigPackageDetailResult;
//import com.store.common.bean.result.QueryCustomerServiceListResult;
//import com.store.common.bean.result.QueryDeliveryOrderDetailResult;
//import com.store.common.bean.result.QueryEmpIncomeDetailResult;
//import com.store.common.bean.result.QueryEmpIncomeListResult;
//import com.store.common.bean.result.QueryGiveOrderDetailResult;
//import com.store.common.bean.result.QueryGiveOrderNumResult;
//import com.store.common.bean.result.QueryGiveOrdersResult;
//import com.store.common.bean.result.QueryGroupOrderByMobileResult;
//import com.store.common.bean.result.QueryInventoryDetailResult;
//import com.store.common.bean.result.QueryInventoryListResult;
//import com.store.common.bean.result.QueryNoticeLogsResult;
//import com.store.common.bean.result.QueryOrderDetailResult;
//import com.store.common.bean.result.QueryOrderListResult;
//import com.store.common.bean.result.QueryProductDivideListResult;
//import com.store.common.bean.result.QueryProductPriceChangeListResult;
//import com.store.common.bean.result.QueryProductStockAndActivityResult;
//import com.store.common.bean.result.QueryReturnOrderDetailResult;
//import com.store.common.bean.result.QueryReturnOrdersResult;
//import com.store.common.bean.result.QuerySFHomeOrderDetailResult;
//import com.store.common.bean.result.QuerySFHomeOrderListResult;
//import com.store.common.bean.result.QueryStoreBigPackageListResult;
//import com.store.common.bean.result.QueryUnreadTotalNumResult;
//import com.store.common.bean.result.QueryWaitingForSignOrderResult;
//import com.store.common.bean.result.QueryWareBhProductListResult;
//import com.store.common.bean.result.QueryWeekDataResult;
//import com.store.common.bean.result.RepenishMentOrderResult;
//import com.store.common.bean.result.ResultDTO;
//import com.store.common.bean.result.SFHomeOrderDetailResult;
//import com.store.common.bean.result.SaveReplenishmentOrderResult;
//import com.store.common.bean.result.SearchPurchasableResult;
//import com.store.common.bean.result.SelectAbnormalCauseListResult;
//import com.store.common.bean.result.ShowInventoryCartResult;
//import com.store.common.bean.result.SignCacheOrderListResult;
//import com.store.common.bean.result.StockErrorOrderDetailsResult;
//import com.store.common.bean.result.StockErrorOrderResult;
//import com.store.common.bean.result.StockHandMadeCarResult;
//import com.store.common.bean.result.StoreDetailByCodeResult;
//import com.store.common.bean.result.StoreIsAutoWareResult;
//import com.store.common.bean.result.SubmitPurchaseOrderResult;
//import com.store.common.bean.result.SysmsgListResult;
//import com.store.common.bean.result.TodayNoticNumResult;
//import com.store.common.bean.result.UnDoNumResult;
//import com.store.common.bean.result.UpdateProductLossResult;
//import com.store.common.bean.result.VersionUpgradeResult;
//import com.store.common.bean.result.WaitingForSignOrderListResult;
//
//import java.util.Map;
//
//import io.reactivex.Observable;
//import okhttp3.RequestBody;
//import retrofit2.http.Body;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.Header;
//import retrofit2.http.Headers;
//import retrofit2.http.Multipart;
//import retrofit2.http.POST;
//import retrofit2.http.PartMap;
//import retrofit2.http.Path;
//
//
///**
// * Created by Acorn on 2018/4/23.
// */
//
//public interface HttpService {
//
//    /**
//     * 登录
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("usercenter/api/v2/a/user/login")
//    Observable<LoginResult> login(@Body RequestBody param);
//
//    /**
//     * 退出登录
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("usercenter/api/v1/a/user/loginOut")
//    Observable<LoginOutResult> loginOut();
//
//    /**
//     * 获取用户信息
//     *
//     * @param token
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("usercenter/api/v1/a/user/getUserInfo")
//    Observable<GetUserInfoResult> getUserInfo(@Header("ssi-token") String token);
//
//    /**
//     * 获取消息数量
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/queryUnreadTotalNum")
//    Observable<QueryUnreadTotalNumResult> queryUnreadTotalNum(@Body RequestBody param);
//
//    /**
//     * 优选到家接单,送货消息数量
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/delivery/queryGiveOrderNum")
//    Observable<QueryGiveOrderNumResult> queryGiveOrderNum();
//
//    /**
//     * 系统消息
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/sysmsgList")
//    Observable<SysmsgListResult> sysmsgList(@Body RequestBody param);
//
//    /**
//     * 系统消息
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/messagesList")
//    Observable<SysmsgListResult> messagesList(@Body RequestBody param);
//
//    /**
//     * 系统消息设为已读
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/messageread")
//    Observable<BaseResult> messageread(@Body RequestBody param);
//
//    /**
//     * 价格变更
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/queryProductPriceChangeList")
//    Observable<QueryProductPriceChangeListResult> queryProductPriceChangeList(@Body RequestBody
//                                                                                      param);
//
//    /**
//     * 分成变更
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/queryProductDivideList")
//    Observable<QueryProductDivideListResult> queryProductDivideList(@Body RequestBody param);
//
//    /**
//     * 促销活动
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/queryActivityList")
//    Observable<QueryActivityListResult> queryActivityList(@Body RequestBody param);
//
//    /**
//     * 当天通知数量
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("message/api/v1/a/msg/todayNoticNum")
//    Observable<TodayNoticNumResult> todayNoticNum();
//
//    /**
//     * 公告列表
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/queryNoticeLogs")
//    Observable<QueryNoticeLogsResult> queryNoticeLogs(@Body RequestBody param);
//
//    /**
//     * 公告详情
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("message/api/v1/a/msg/getGonggaoDetail/{announceId}")
//    Observable<GetGonggaoDetailResult> getGonggaoDetail(@Path("announceId") int announceId);
//
//    /**
//     * 将公告置为已读
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("message/api/v1/a/msg/settingAnnounceIsRead")
//    Observable<BaseResult> settingAnnounceIsRead(@Body RequestBody param);
//
//    /**
//     * 查询库存盘点列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/queryInventoryList")
//    Observable<QueryInventoryListResult> queryInventoryList(@Body RequestBody param);
//
//    /**
//     * 删除盘点人接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/deleteInventoryPerson")
//    Observable<DeleteProductLossResult> deleteInventoryPerson(@Body RequestBody param);
//
//    /**
//     * 添加或修改盘点人接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/addInventoryPerson")
//    Observable<DeleteProductLossResult> addInventoryPerson(@Body RequestBody param);
//
//    /**
//     * 获取盘点人列表接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/inventory/getInventoryPersonList")
//    Observable<GetInventoryPersonListResult> getInventoryPersonList();
//
//    /**
//     * 设置默认盘点人接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/setDefaultInventoryPerson")
//    Observable<DeleteProductLossResult> setDefaultInventoryPerson(@Body RequestBody param);
//
//
//    /**
//     * 单个商品加入盘点接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/addInventory")
//    Observable<AddInventoryResult> addInventory(@Body RequestBody param);
//
//    /**
//     * 单个商品删除盘点接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/deleteInventory")
//    Observable<AddInventoryResult> deleteInventory(@Body RequestBody param);
//
//    /**
//     * 批量商品删除盘点接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/batchDeleteInventory")
//    Observable<AddInventoryResult> batchDeleteInventory(@Body RequestBody param);
//
//    /**
//     * 判断是否能进货
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/queryStoreOrganizeList")
//    Observable<ResultDTO> queryStoreOrganizeList();
//
//    /**
//     * 批量商品加入盘点接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/batchAddInventory")
//    Observable<AddInventoryResult> batchAddInventory(@Body RequestBody param);
//
//    /**
//     * 提交盘点接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/commitInventory")
//    Observable<CommitProductLossResult> commitInventory(@Body RequestBody param);
//
//    /**
//     * 扫码更新盘点购物车接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/updateScanInventory")
//    Observable<AddInventoryResult> updateScanInventory(@Body RequestBody param);
//
//    /**
//     * 获取进货购物车信息
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/getCart")
//    Observable<StockHandMadeCarResult> getCart(@Body RequestBody param);
//
//    /**
//     * 更新盘点购物车接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/updateInventory")
//    Observable<DeleteProductLossResult> updateInventory(@Body RequestBody param);
//
//    /**
//     * 查询盘点购物车接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/inventory/showInventoryCart")
//    Observable<ShowInventoryCartResult> showInventoryCart();
//
//
//    /**
//     * 进货商品列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/searchPurchasable")
//    Observable<SearchPurchasableResult> searchPurchasable(@Body RequestBody param);
//
//    /**
//     * 添加进货购物车
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/addCart")
//    Observable<StockHandMadeCarResult> addCart(@Body RequestBody param);
//
//
//    /**
//     * 添加进货购物车
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/batchAddCart")
//    Observable<AddCartResult> batchAddCart(@Body RequestBody param);
//
//    /**
//     * 提交进货单
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/submitPurchaseOrder")
//    Observable<SubmitPurchaseOrderResult> submitPurchaseOrder(@Body RequestBody param);
//
//    /**
//     * 删除进货购物车
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/deleteFromCartById")
//    Observable<StockHandMadeCarResult> deleteFromCartById(@Body RequestBody param);
//
//    /**
//     * 更新进货购物车
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/updateCart")
//    Observable<StockHandMadeCarResult> updateCart(@Body RequestBody param);
//
//    /**
//     * 添加进货购物车
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("usercenter/api/v1/a/user/versionUpgrade")
//    Observable<VersionUpgradeResult> versionUpgrade(@Body RequestBody param);
//
//
//    /**
//     * 团购查单
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("tool/api/v1/a/group/queryGroupOrderByMobile")
//    Observable<QueryGroupOrderByMobileResult> queryGroupOrderByMobile(@Body RequestBody param);
//
//    /**
//     * 快查商品信息、库存信息以及商品活动信息接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/product/queryProductStockAndActivity")
//    Observable<QueryProductStockAndActivityResult> queryProductStockAndActivity(@Body RequestBody param);
//
//    /**
//     * 快查商品周销售数据接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/product/queryWeekData")
//    Observable<QueryWeekDataResult> queryWeekData(@Body RequestBody param);
//
//    /**
//     * 查询历史盘点列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/queryHistoryInventoryList")
//    Observable<HistoryInventoryInfoVoResult> queryHistoryInventoryList(@Body RequestBody param);
//
//    /**
//     * 查询盘点详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/queryHistoryInventoryDetail")
//    Observable<QueryInventoryDetailResult> queryHistoryInventoryDetail(@Body RequestBody param);
//
//    /**
//     * 加入确认待签收列表缓存
//     * <p>
//     * ign* @param param
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/addOrderProductListCache")
//    Observable<BaseResult> addOrderProductListCache(@Body RequestBody param);
//
//    /**
//     * 更新待签收商品列表缓存
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/updateOrderProductListCache")
//    Observable<BaseResult> updateOrderProductListCache(@Body RequestBody param);
//
//    /**
//     * 获取代签收列表缓存
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/getOrderProductListCache")
//    Observable<SignCacheOrderListResult> getOrderProductListCache();
//
//    /**
//     * 获取代签收列表缓存
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/deleteOrderProductListCache")
//    Observable<BaseResult> deleteOrderProductListCache(@Body RequestBody param);
//
//    /**
//     * 盘点获取商品一级分类接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/inventory/getCategoryList")
//    Observable<CategoryResult> getCategoryList();
//
//    /**
//     * 获取商品SP组品类中心接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/inventory/queryCategorySpList")
//    Observable<CategoryResult> queryCategorySpList();
//
//    /**
//     * 获取授信额度接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/ireplenish/getStoreDetailByCode")
//    Observable<StoreDetailByCodeResult> getStoreDetailByCode();
//
//    /**
//     * 获取是否自动补货接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/ireplenish/getStoreIsAutoWare")
//    Observable<StoreIsAutoWareResult> getStoreIsAutoWare();
//
//    /**
//     * 盘点获取商品二级分类接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/inventory/getCategoryChildrenList")
//    Observable<CategoryResult> getCategoryChildrenList(@Body RequestBody param);
//
//    /**
//     * 查询补货单详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/queryBhOrderDetail")
//    Observable<BhOrderDetailsResult> queryBhOrderDetail(@Body RequestBody param);
//
//    /**
//     * 查询商品报损列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/queryProductLossList")
//    Observable<ProductLossInfoVoResult> queryProductLossList(@Body RequestBody param);
//
//    /**
//     * 加入或删除补货意向接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/modifyWareBhProduct")
//    Observable<ModifyWareBhProductResult> modifyWareBhProduct(@Body RequestBody param);
//
//    /**
//     * 获取自营子订单操作状态
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/findShippedOrderType")
//    Observable<FindShippedOrderTypeResult> findShippedOrderType(@Body RequestBody param);
//
//
//    /**
//     * 获取自营进货单详情
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/findShippedOrderDetail")
//    Observable<FindShippedOrderDetailResult> findShippedOrderDetail(@Body RequestBody param);
//
//
//    /**
//     * 获取扫码代签收列表
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/queryWaitingForSignOrder")
//    Observable<QueryWaitingForSignOrderResult> queryWaitingForSignOrder(@Body RequestBody param);
//
//    /**
//     * 获取扫码代签收列表查商品
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/queryProductByBarcode")
//    Observable<QueryProductByBarcodeResult> queryProductByBarcode(@Body RequestBody param);
//
//
//    /**
//     * 查询自动补货商品列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/queryWareBhProductList")
//    Observable<QueryWareBhProductListResult> queryWareBhProductList(@Body RequestBody param);
//
//    /**
//     * 查询历史报损列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/queryHistoryLossList")
//    Observable<HistoryLossInfoVo> queryHistoryLossList(@Body RequestBody param);
//
//    /**
//     * 提交补货单接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/commitBhOrder")
//    Observable<CommitSucessResult> commitBhOrder(@Body RequestBody param);
//
//    /**
//     * 取消补货单接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/cancelBhOrder")
//    Observable<CommitSucessResult> cancelBhOrder(@Body RequestBody param);
//
//    /**
//     * 加入报损接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/addProductLoss")
//    Observable<ProductLossCartVo> addProductLoss(@Body RequestBody param);
//
//    /**
//     * 查看异常单列表
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/findUnusualOrderVoListPage")
//    Observable<StockErrorOrderResult> findUnusualOrderVoListPage(@Body RequestBody param);
//
//    /**
//     * 删除报损商品接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/deleteProductLoss")
//    Observable<UpdateProductLossResult> deleteProductLoss(@Body RequestBody param);
//
//    /**
//     * 查询历史报损详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/queryHistoryLossDetail")
//    Observable<LossDetailVoResult> queryHistoryLossDetail(@Body RequestBody param);
//
//    /**
//     * 首页返回数据接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderManage/queryDeliverHomeNum")
//    Observable<DeliverHomeNumBean> queryDeliverHomeNum();
//
//
//    /**
//     * 查询报损购物车接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/showLossCart")
//    Observable<ProductLossCartVo> showLossCart(@Body RequestBody param);
//
//    /**
//     * 更新报损商品接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/productLoss/updateProductLoss")
//    Observable<UpdateProductLossResult> updateProductLoss(@Body RequestBody param);
//
//    /**
//     * 查询日常报损与购销报损数量接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @GET("replenish/api/v1/a/productLoss/queryProductLossCount")
//    Observable<ProductLossCartVo> queryProductLossCount();
//
//    /**
//     * 提交商品报损接口
//     *
//     * @param map
//     * @return
//     */
//    @POST("replenish/api/v1/a/productLoss/commitProductLoss")
//    @Multipart
//    Observable<CommitProductLossResult> commitProductLoss(@PartMap Map<String, RequestBody> map);
//
//    /**
//     * 扫码购核验
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/order/queryOrderDetail")
//    Observable<QueryOrderDetailResult> queryOrderDetail(@Body RequestBody param);
//
//    /**
//     * 大包裹列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/receive/queryStoreBigPackageList")
//    Observable<QueryStoreBigPackageListResult> queryStoreBigPackageList(@Body RequestBody param);
//
//    /**
//     * 大包裹详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/receive/queryBigPackageDetail")
//    Observable<QueryBigPackageDetailResult> queryBigPackageDetail(@Body RequestBody param);
//
//
//    /**
//     * 大包裹签收接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/receive/doBigPackageRec")
//    Observable<BaseDataResult<Integer>> doBigPackageRec(@Body RequestBody param);
//
//
//    /**
//     * 分拣单列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderSorting/getSortingOrderList")
//    Observable<GetSortingOrderListResult> getSortingOrderList(@Body RequestBody param);
//
//
//    /**
//     * 自动补货单单列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/queryBhOrderList")
//    Observable<RepenishMentOrderResult> queryBhOrderList(@Body RequestBody param);
//
//    /**
//     * 获取顺丰家未处理订单数量接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/unDoNum")
//    Observable<UnDoNumResult> unDoNum();
//
//
//    /**
//     * 优选到家->查询订单列表
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/querySFHomeOrderList")
//    Observable<QuerySFHomeOrderListResult> querySFHomeOrderList(@Body RequestBody param);
//
//    /**
//     * 优选到家订单详情
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/querySFHomeOrderDetail")
//    Observable<QuerySFHomeOrderDetailResult> querySFHomeOrderDetail(@Body RequestBody param);
//
//    /**
//     * 优选到家取消订单详情接口
//     *
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("give/api/v1/a/message/getCustomerServiceListTCP")
//    Observable<GetCustomerServiceListTCPResult> getCustomerServiceListTCP(@Field("id") int id, @Field("thirdGiveState") int thirdGiveState);
//
//    /**
//     * 顺丰家订单详情查询接口（不含售后服务）
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/sFHomeOrderDetail")
//    Observable<SFHomeOrderDetailResult> sFHomeOrderDetail(@Body RequestBody param);
//
//
//    /**
//     * 顺丰家售后申请列表与详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/queryCustomerServiceList")
//    Observable<QueryCustomerServiceListResult> queryCustomerServiceList(@Body RequestBody param);
//
//
//    /**
//     * 顺丰家接单人列表接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/getPartInfo")
//    Observable<GetPartInfoResult> getPartInfo();
//
//    /**
//     * 顺丰家接单接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/sfhome/receiveOrders")
//    Observable<BaseDataResult<Boolean>> receiveOrders(@Body RequestBody param);
//
//    /**
//     * 查询员工收入列表接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/deliveryIncome/queryEmpIncomeList")
//    Observable<QueryEmpIncomeListResult> queryEmpIncomeList(@Body RequestBody param);
//
//
//    /**
//     * 查询员工收入明细接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/deliveryIncome/queryEmpIncomeDetail")
//    Observable<QueryEmpIncomeDetailResult> queryEmpIncomeDetail(@Body RequestBody param);
//
//
//    /**
//     * 查询配送单列表接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/delivery/queryGiveOrders")
//    Observable<QueryGiveOrdersResult> queryGiveOrders(@Body RequestBody param);
//
//    /**
//     * 待签收订单列表
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/queryWaitingForSignOrderList")
//    Observable<WaitingForSignOrderListResult> queryWaitingForSignOrderList(@Body RequestBody param);
//
//
//    //    /**
////     * 自动补货商品列表接口
////     *
////     * @param param
////     * @return
////     */
////    @Headers({"Content-Type: application/json", "Accept: application/json"})
////    @POST("replenish/api/v1/a/wareBh/queryWareBhProductList")
////    Observable<WareBhProductResult> queryWareBhProductList(@Body RequestBody param);
//
//    /**
//     * 获取自动补货商品分类接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/wareBh/getSubCategory")
//    Observable<GshopCategoryResult> getSubCategory(@Body RequestBody param);
//
//
//    /**
//     * 异常单列表查询接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/exceptionOrder/getExOrderList")
//    Observable<GetExOrderListResult> getExOrderList(@Body RequestBody param);
//
//
//    /**
//     * 进货模块异常单详情
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/queryUnusualInfoDetail")
//    Observable<StockErrorOrderDetailsResult> queryUnusualInfoDetail(@Body RequestBody param);
//
//    /**
//     * 异常单详情查询接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/exceptionOrder/getExOrderInfo")
//    Observable<GetExOrderInfoResult> getExOrderInfo(@Body RequestBody param);
//
//
//    /**
//     * 异常单详情修改接口
//     *
//     * @return
//     */
//    @Multipart
//    @POST("give/api/v1/a/exceptionOrder/updateExOrder")
//    Observable<BaseDataResult<Boolean>> updateExOrder(@PartMap Map<String, RequestBody> map);
//
//
//    /**
//     * 生成配送单接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderSorting/createDeliveryOrder")
//    Observable<BaseResult> createDeliveryOrder(@Body RequestBody param);
//
//    /**
//     * 分拣单详情
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderSorting/getSortingOrderDetail")
//    Observable<GetSortingOrderDetailResult> getSortingOrderDetail(@Body RequestBody param);
//
//    /**
//     * 确定分拣：检测分拣单是否需异常分拣接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderSorting/checkSortingOrderStatus")
//    Observable<BaseDataResult<Integer>> checkSortingOrderStatus(@Body RequestBody param);
//
//
//    /**
//     * 生成异常单接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderSorting/createExceptionOrder")
//    Observable<BaseDataResult<String>> createExceptionOrder(@Body RequestBody param);
//
//
//    /**
//     * 查询配送单详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/delivery/queryGiveOrderDetail")
//    Observable<QueryGiveOrderDetailResult> queryGiveOrderDetail(@Body RequestBody param);
//
//
//    /**
//     * 开始配送接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/delivery/startDelivery")
//    Observable<BaseDataResult<Boolean>> startDelivery(@Body RequestBody param);
//
//
//    /**
//     * 完成配送接口
//     *
//     * @return
//     */
//    @Multipart
//    @POST("give/api/v1/a/delivery/finishDelivery")
//    Observable<BaseDataResult<Boolean>> finishDelivery(@PartMap Map<String, RequestBody> map);
//
//    /**
//     * 查询退仓单列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/returnOrder/queryReturnOrders")
//    Observable<QueryReturnOrdersResult> queryReturnOrders(@Body RequestBody param);
//
//
//    /**
//     * 查询退仓单详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/returnOrder/queryReturnOrderDetail")
//    Observable<QueryReturnOrderDetailResult> queryReturnOrderDetail(@Body RequestBody param);
//
//
//    /**
//     * 顺丰家确认签收接口
//     *
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("give/api/v1/a/sfhome/customerSureSign")
//    Observable<BaseDataResult<Boolean>> customerSureSign(@PartMap Map<String, RequestBody> map);
//
//
//    /**
//     * 订单列表接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderManage/queryOrderList")
//    Observable<QueryOrderListResult> queryOrderList(@Body RequestBody param);
//
//
//    /**
//     * 订单详情接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("give/api/v1/a/orderManage/queryOrderDetail")
//    Observable<QueryDeliveryOrderDetailResult> queryDeliveryOrderDetail(@Body RequestBody param);
//
//    /**
//     * 查询进货单列表
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/findPurchaseOrderListPage")
//    Observable<FindPurchaseOrderListPageResult> findPurchaseOrderListPage(@Body RequestBody param);
//
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/findPurchaseOrderDetail")
//    Observable<FindPurchaseOrderDetailResult> stockFindPurchaseOrderDetail(@Body RequestBody param);
//
//    /**
//     * 取消商家进货单接口
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/cancelOrder")
//    Observable<BaseResult> cancelOrder(@Body RequestBody param);
//
//
//    /**
//     * 手工进货-提交异常单
//     *
//     * @return
//     */
//    @Multipart
//    @POST("replenish/api/v1/a/oreplenish/commitProductUnusual")
//    Observable<BaseDataResult<Boolean>> commitProductUnusual(@PartMap Map<String, RequestBody> map);
//
//    /**
//     * 签收商家进货单接口
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/receivePurchaseOrder")
//    Observable<BaseResult> receivePurchaseOrder(@Body RequestBody param);
//
//    /**
//     * 补收自营进货单
//     *
//     * @param param
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/oreplenish/saveReplenishmentOrder")
//    Observable<SaveReplenishmentOrderResult> saveReplenishmentOrder(@Body RequestBody param);
//
//
//    /**
//     * 签收商家进货单接口
//     *
//     * @return
//     */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("replenish/api/v1/a/ireplenish/getPurchaseStatusCount")
//    Observable<PurchaseStatusCountResult> getPurchaseStatusCount();
//
//    /**
//     * 异常配送接口
//     *
//     * @param param
//     * @return
//     */
//    @POST("give/api/v1/a/delivery/abnormalDelivery")
//    Observable<AbnormalDeliveryResult> abnormalDelivery(@Body RequestBody param);
//
//    /**
//     * 异常配送接口
//     *
//     * @param param
//     * @return
//     */
//    @POST("give/api/v1/a/delivery/selectAbnormalCauseList")
//    Observable<SelectAbnormalCauseListResult> selectAbnormalCauseList(@Body RequestBody param);
//}
