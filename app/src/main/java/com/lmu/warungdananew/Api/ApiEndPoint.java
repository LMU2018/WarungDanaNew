package com.lmu.warungdananew.Api;

import com.lmu.warungdananew.Response.CheckContact;
import com.lmu.warungdananew.Response.CheckNIK;
import com.lmu.warungdananew.Response.CheckNewDB;
import com.lmu.warungdananew.Response.DetailAdditionalContact;
import com.lmu.warungdananew.Response.DetailContact;
import com.lmu.warungdananew.Response.DetailContactAddress;
import com.lmu.warungdananew.Response.DetailJadwal;
import com.lmu.warungdananew.Response.DetailLead;
import com.lmu.warungdananew.Response.DetailOrder;
import com.lmu.warungdananew.Response.DetailOrderLoan;
import com.lmu.warungdananew.Response.DetailOrderSurety;
import com.lmu.warungdananew.Response.DetailProductUFI;
import com.lmu.warungdananew.Response.DetailTarget;
import com.lmu.warungdananew.Response.DetailUpdateVersion;
import com.lmu.warungdananew.Response.DetailUserAgreement;
import com.lmu.warungdananew.Response.Login;
import com.lmu.warungdananew.Response.OrderReasonDetail;
import com.lmu.warungdananew.Response.RespCounterBrosur;
import com.lmu.warungdananew.Response.RespCounterLead;
import com.lmu.warungdananew.Response.RespDeviceID;
import com.lmu.warungdananew.Response.RespKPICfa;
import com.lmu.warungdananew.Response.RespListAddress;
import com.lmu.warungdananew.Response.RespListAlamat;
import com.lmu.warungdananew.Response.RespListCabangFif;
import com.lmu.warungdananew.Response.RespListCategoryAddress;
import com.lmu.warungdananew.Response.RespListContact;
import com.lmu.warungdananew.Response.RespListContactCollaborate;
import com.lmu.warungdananew.Response.RespListDataSource;
import com.lmu.warungdananew.Response.RespListEmployee;
import com.lmu.warungdananew.Response.RespListJadwal;
import com.lmu.warungdananew.Response.RespListJob;
import com.lmu.warungdananew.Response.RespListLead;
import com.lmu.warungdananew.Response.RespListLeadCollab;
import com.lmu.warungdananew.Response.RespListLog;
import com.lmu.warungdananew.Response.RespListLogDesc;
import com.lmu.warungdananew.Response.RespListLogStatus;
import com.lmu.warungdananew.Response.RespListLogTarget;
import com.lmu.warungdananew.Response.RespListMarital;
import com.lmu.warungdananew.Response.RespListNeed;
import com.lmu.warungdananew.Response.RespListNote;
import com.lmu.warungdananew.Response.RespListOrder;
import com.lmu.warungdananew.Response.RespListPhone;
import com.lmu.warungdananew.Response.RespListPlace;
import com.lmu.warungdananew.Response.RespListProduct;
import com.lmu.warungdananew.Response.RespListReligion;
import com.lmu.warungdananew.Response.RespListReportJadwal;
import com.lmu.warungdananew.Response.RespListTarget;
import com.lmu.warungdananew.Response.RespListUnit;
import com.lmu.warungdananew.Response.RespListUnitList;
import com.lmu.warungdananew.Response.RespListUnitUfi;
import com.lmu.warungdananew.Response.RespListUserJadwal;
import com.lmu.warungdananew.Response.RespListUserLog;
import com.lmu.warungdananew.Response.RespListVisum;
import com.lmu.warungdananew.Response.RespOrderDocument;
import com.lmu.warungdananew.Response.RespPost;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @Multipart
    @POST("visum_upload_photo")
    Call<RespPost> uploadVisumPhoto(@Part("id_target") RequestBody idTarget,
                                         @Part("id_target_visum") RequestBody udTargetVisum,
                                         @Part MultipartBody.Part photo,
                                         @Part("id_cms_users") RequestBody idUser);



    @GET("check_android_id")
    Call<RespDeviceID> checkAndroidID(@Query("id") int uID,
                                      @Query("device_id") String device_id);

    @FormUrlEncoded
    @POST("update_android_id")
    Call<RespPost> updateAndroidID(@Field("id") Integer id_cms,
                                          @Field("device_id") String device_id);

    @GET("lead_search")
    Call<RespListLead> leadSearch(@Query("first_name") String firstName,
                                   @Query("id_cms_users") Integer idUser);

    @Multipart
    @POST("order_photo_update")
    Call<RespPost> updateOrderPhoto(@Part("id") RequestBody id,
                                    @Part("id_order") RequestBody id_order,
                                    @Part MultipartBody.Part photo,
                                    @Part("description") RequestBody description,
                                    @Part("updated_by") RequestBody updated_by);

    @GET("order_photo_delete")
    Call<RespPost> deleteOrderPhoto(@Query("id") Integer id);

    @GET("order_photo_listing")
    Call<RespOrderDocument> orderPhotoListing(@Query("id_order") Integer id_order);

    @Multipart
    @POST("order_upload_photo")
    Call<RespPost> orderUploadPhoto(@Part("id_order") RequestBody id_order,
                                    @Part MultipartBody.Part photo,
                                    @Part("description") RequestBody description,
                                    @Part("id_cms_users") RequestBody id_cms_users);

    @GET("order_search")
    Call<RespListOrder> orderSearch(@Query("first_name") String firstName,
                                    @Query("id_cms_users") Integer idUser);

    @GET("contact_search")
    Call<RespListContact> contactSearch(@Query("first_name") String firstName,
                                        @Query("last_name") String lastName,
                                        @Query("id_cms_users") Integer idUser);

    @GET("target_search")
    Call<RespListTarget> targetSearch(@Query("first_name") String firstName,
                                      @Query("last_name") String lastName,
                                      @Query("id_cms_users") Integer idUser);

    @FormUrlEncoded
    @POST("ubah_password")
    Call<ResponseBody> ubahPasword(@Field("id") Integer id,
                                   @Field("password") String password);

    @GET("order_reason")
    Call<OrderReasonDetail> order_reason(@Query("id") Integer idUser);

    @GET("lead_listing_collab")
    Call<RespListLeadCollab> listLeadCollab(@Query("id_cms_users") Integer id,
                                            @Query("limit") Integer limit,
                                            @Query("offset") Integer offset);

    @GET("lead_listing")
    Call<RespListLead> listLeadFav(@Query("id_cms_users") Integer id,
                                   @Query("favorite") Integer favorite);

    @GET("lead_listing")
    Call<RespListLead> listLeadFavPagg(@Query("id_cms_users") Integer id,
                                       @Query("favorite") Integer favorite,
                                       @Query("limit") Integer limit,
                                       @Query("offset") Integer offset);

    @GET("lead_collab_check")
    Call<RespPost> leadCollabCheck(@Query("id_cms_users") Integer id_cms_users,
                                   @Query("id_lead") Integer id_lead);

    @FormUrlEncoded
    @POST("lead_collab_create")
    Call<ResponseBody> lead_collab_create(@Field("id_lead") Integer id_lead,
                                          @Field("id_cms_users") Integer id_cms_users);

    @FormUrlEncoded
    @POST("lead_update")
    Call<ResponseBody> leadUpdateFavorite(@Field("id") Integer idLead,
                                          @Field("favorite") Integer favorite,
                                          @Field("updated_by") Integer iduser);

    @GET("lead_listing")
    Call<RespListLead> listLead(@Query("id_cms_users") Integer id,
                                @Query("id_lead_mst_status") Integer idStatus);

    @GET("lead_listing")
    Call<RespListLead> listLeadPagg(@Query("id_cms_users") Integer id,
                                    @Query("id_lead_mst_status") Integer idStatus,
                                    @Query("limit") Integer limit,
                                    @Query("offset") Integer Offset);

    @GET("counter_new_db")
    Call<RespCounterLead> leadCounter(@Query("id_cms_users") Integer id);

    @GET("counter_target")
    Call<RespCounterLead> targetCounter(@Query("id_cms_users") Integer id);

    @GET("counter_order")
    Call<RespCounterLead> orderCounter(@Query("id_cms_users") Integer id);

    @GET("counter_booking")
    Call<RespCounterLead> bookingCounter(@Query("id_cms_users") Integer id);

    @GET("counter_brosur")
    Call<RespCounterBrosur> brosurCounter(@Query("id_cms_users") Integer id);

    @GET("counter_order")
    Call<RespCounterLead> orderCounterOutlet(@Query("id_mst_outlet") Integer id);

    @GET("counter_booking")
    Call<RespCounterLead> bookingCounterOutlet(@Query("id_mst_outlet") Integer id);

    @GET("user_log_listing")
    Call<RespListUserLog> userLogListing(@Query("id_cms_users") Integer id_cms_users,
                                         @Query("limit") Integer limit,
                                         @Query("offset") Integer offset);

    @FormUrlEncoded
    @POST("user_log_create")
    Call<ResponseBody> userLogCreate(@Field("id_modul") Integer idModul,
                                     @Field("id_data") Integer id_data,
                                     @Field("jenis") String jenis,
                                     @Field("id_cms_users") Integer userid);

    @GET("lead_detail")
    Call<DetailLead> leadDetail(@Query("id") Integer id);

    @GET("lead_phone_listing")
    Call<RespListPhone> listPhoneLead(@Query("id_lead") Integer id);

    @GET("lead_address_listing")
    Call<RespListAddress> listAddressLead(@Query("id_lead") Integer id);

    @GET("lead_product_listing")
    Call<RespListUnit> listUnit(@Query("id_lead") Integer id);

    @GET("lead_log_listing")
    Call<RespListLog> listLog(@Query("id_lead") Integer id);

    @GET("lead_note_listing")
    Call<RespListNote> listNote(@Query("id_lead") Integer id);

    @FormUrlEncoded
    @POST("lead_create")
    Call<RespPost> leadCreate(@Field("id_mst_outlet") Integer idOutlet,
                              @Field("id_mst_data_source") Integer idSource,
                              @Field("id_cms_users") Integer userid,
                              @Field("first_name") String firstName,
                              @Field("last_name") String lastName,
                              @Field("id_mst_job") Integer idJob,
                              @Field("id_lead_mst_status") Integer idleadStatus);

    @FormUrlEncoded
    @POST("lead_phone_create")
    Call<ResponseBody> leadPhoneCreate(@Field("id_lead") Integer idLead,
                                       @Field("number") String number,
                                       @Field("status") String status,
                                       @Field("id_cms_users") Integer userid);

    @FormUrlEncoded
    @POST("lead_product_create")
    Call<RespPost> leadProductCreate(@Field("id_lead") Integer idLead,
                                     @Field("id_mst_product") Integer idMstProduct,
                                     @Field("id_cms_users") Integer userid);

    @FormUrlEncoded
    @POST("lead_product_detail_create")
    Call<ResponseBody> leadProductDetailCreate(@Field("id_lead_product") Integer idLeadProduct,
                                               @Field("id_mst_unit") Integer idMstUnit,
                                               @Field("id_cms_users") Integer userid,
                                               @Field("nopol") String nopol,
                                               @Field("tax_status") String tax,
                                               @Field("owner") String owner);

    @FormUrlEncoded
    @POST("lead_product_detail_update")
    Call<ResponseBody> leadProductDetailUpdate(@Field("id") Integer id,
                                               @Field("id_mst_unit") Integer idUnit,
                                               @Field("nopol") String nopol,
                                               @Field("tax_status") String taxStatus,
                                               @Field("owner") String owner,
                                               @Field("updated_by") Integer idUser);


    @FormUrlEncoded
    @POST("lead_address_create")
    Call<ResponseBody> leadAddressCreate(@Field("id_lead") Integer idLead,
                                         @Field("address") String address,
                                         @Field("id_cms_users") Integer userid,
                                         @Field("id_mst_address") Integer idAddress,
                                         @Field("id_mst_category_address") Integer idcategoryAddress);

    @FormUrlEncoded
    @POST("lead_log_create")
    Call<ResponseBody> leadLog(@Field("id_lead") Integer idLead,
                               @Field("duration") String duration,
                               @Field("id_mst_log_desc") Integer idDesc,
                               @Field("recall") String recall,
                               @Field("id_cms_users") Integer iduser);

    @FormUrlEncoded
    @POST("lead_update")
    Call<ResponseBody> leadUpdateStatus(@Field("id") Integer idLead,
                                        @Field("id_lead_mst_status") Integer idstatus,
                                        @Field("updated_by") Integer iduser);

    @FormUrlEncoded
    @POST("lead_update")
    Call<ResponseBody> leadUpdate(@Field("id") Integer idLead,
                                  @Field("first_name") String firstname,
                                  @Field("last_name") String lastname,
                                  @Field("id_mst_job") Integer idJob,
                                  @Field("id_mst_data_source") Integer idSource,
                                  @Field("id_lead_mst_status") Integer idstatus,
                                  @Field("updated_by") Integer iduser);

    @FormUrlEncoded
    @POST("lead_address_update")
    Call<ResponseBody> leadAddressUpdate(@Field("id") Integer id,
                                         @Field("id_lead") Integer idLead,
                                         @Field("address") String address,
                                         @Field("id_mst_address") Integer idMstAddress,
                                         @Field("id_mst_category_address") Integer idMstCat,
                                         @Field("updated_by") Integer idUser);


    @FormUrlEncoded
    @POST("lead_note_create")
    Call<ResponseBody> leadNoteCreate(@Field("id_lead") Integer idLead,
                                      @Field("id_cms_users") Integer idUser,
                                      @Field("note") String note);

    @FormUrlEncoded
    @POST("login")
    Call<Login> loginrequest(@Field("npm") String npm,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("user_agreement_check")
    Call<DetailUserAgreement> eulaCheck(@Field("id_cms_users") Integer idUser);

    @FormUrlEncoded
    @POST("user_apps_logs_create")
    Call<ResponseBody> userLogs(@Field("useragent") String userAgent,
                                @Field("description") String desc,
                                @Field("id_cms_users") Integer idUser);

    @FormUrlEncoded
    @POST("user_agreement_create")
    Call<ResponseBody> eulaCreate(@Field("eula") String eula,
                                  @Field("id_cms_users") Integer idUser);


    @GET("mst_category_address")
    Call<RespListCategoryAddress> categoryadress();

    //Tidak Digunakan
    @GET("mst_address")
    Call<RespListAlamat> alamat(@Query("kelurahan") String kelurahan,
                                @Query("kecamatan") String kecamatan,
                                @Query("kabupaten") String kabupaten,
                                @Query("provinsi") String provinsi);

    @GET("mst_job")
    Call<RespListJob> job();

    @GET("mst_data_source")
    Call<RespListDataSource> dataSource();

    //Tidak Digunakan
    @GET("mst_product")
    Call<RespListProduct> product();

    @GET("mst_log_status")
    Call<RespListLogStatus> logStatus();

    @GET("mst_visum_status")
    Call<RespListLogStatus> visumStatus();

    @GET("mst_religion")
    Call<RespListReligion> religion();

    @GET("contact_mst_status_marital")
    Call<RespListMarital> marital();

    @GET("contact_mst_status_employee")
    Call<RespListEmployee> employee();

    @GET("mst_place")
    Call<RespListPlace> place();

    @GET("mst_need_listing")
    Call<RespListNeed> need();

    @GET("mst_log_desc")
    Call<RespListLogDesc> logDesc(@Query("id_mst_log_status") Integer idLogStatus);

    @GET("mst_unit_ufi")
    Call<RespListUnitUfi> unitUfi(@Query("id_mst_branch") Integer idBranch,
                                  @Query("merk") String merk,
                                  @Query("year") String year);

    @GET("mst_cabang_fif")
    Call<RespListCabangFif> cabangFif(@Query("branch_name") String branchFif,
                                      @Query("pos_name") String posFif);

    //    ADA Code
    @GET("mst_unit_ufi_list")
    Call<RespListUnitList> unitList(@Query("id_mst_branch") Integer idBranch,
                                    @Query("merk") String merk,
                                    @Query("year") String year);

    //    Tidak Digunakan
    @GET("kpi_cfa")
    Call<RespKPICfa> kpicfa(@Query("id") Integer idUser);


    @GET("check_phone_new_db")
    Call<CheckNewDB> checkNewDB(@Query("number") String nohp);

    @GET("check_nik")
    Call<CheckNIK> checkNIK(@Query("nik") String nik);

    @GET("check_contact")
    Call<CheckContact> CheckContact(@Query("id_contact") Integer id_contact,
                                    @Query("id_cms_users") Integer idUser);


    @GET("activity_listing")
    Call<RespListJadwal> listJadwal(@Query("activity_schedule_start_date") String tanggal,
                                    @Query("id_cms_users") Integer idUser);

    @GET("activity_detail")
    Call<DetailJadwal> jadwalDetail(@Query("id") Integer id);

    @GET("activity_user_listing")
    Call<RespListUserJadwal> listUserJadwal(@Query("id_activity_schedule") Integer idActivity);

    @GET("activity_report_listing")
    Call<RespListReportJadwal> listReportJadwal(@Query("id_activity_schedule") Integer idActivity);

    @FormUrlEncoded
    @POST("activity_report_create")
    Call<ResponseBody> activityReportCreate(@Field("id_activity_schedule") Integer idSchedule,
                                            @Field("id_cms_users") Integer idUser,
                                            @Field("id_activity_mst_status") Integer idstatus,
                                            @Field("note") String note,
                                            @Field("brosur") String brosur,
                                            @Field("lat") String lat,
                                            @Field("lng") String lng);


    @GET("contact_listing")
    Call<RespListContact> listContact(@Query("id_cms_users") Integer userid);

    @GET("contact_listing")
    Call<RespListContact> listContactPagg(@Query("id_cms_users") Integer userid,
                                          @Query("limit") Integer limit,
                                          @Query("offset") Integer offset);

    @GET("contact_collaborate_listing")
    Call<RespListContactCollaborate> listContactCollab(@Query("id_contact") Integer idContact);

    @GET("contact_detail")
    Call<DetailContact> detailContact(@Query("id") Integer id);

    @GET("contact_additional_detail")
    Call<DetailAdditionalContact> detailAdditionalContact(@Query("id_contact") Integer id);

    @GET("contact_phone_listing")
    Call<RespListPhone> listPhoneContact(@Query("id_contact") Integer id);

    @GET("contact_address_listing")
    Call<RespListAddress> listAddressContact(@Query("id_contact") Integer id);

    @GET("contact_note_listing")
    Call<RespListNote> listNoteContact(@Query("id_contact") Integer id);

    @FormUrlEncoded
    @POST("contact_create")
    Call<RespPost> contactCreate(@Field("nik") String nik,
                                 @Field("first_name") String firstName,
                                 @Field("last_name") String lastName,
                                 @Field("birth_place") String birthPlace,
                                 @Field("birth_date") String birthDate,
                                 @Field("gender") String gender,
                                 @Field("id_mst_religion") Integer idreligion,
                                 @Field("id_contact_mst_status_marital") Integer idmarital,
                                 @Field("id_mst_job") Integer job,
                                 @Field("id_mst_data_source") Integer idSource,
                                 @Field("id_cms_users") Integer userid);

    @FormUrlEncoded
    @POST("contact_update")
    Call<ResponseBody> contactUpdate(@Field("id") Integer id,
                                     @Field("nik") String nik,
                                     @Field("first_name") String firstName,
                                     @Field("last_name") String lastName,
                                     @Field("birth_place") String birthPlace,
                                     @Field("birth_date") String birthDate,
                                     @Field("gender") String gender,
                                     @Field("id_mst_religion") Integer idreligion,
                                     @Field("id_contact_mst_status_marital") Integer idmarital,
                                     @Field("id_mst_job") Integer job,
                                     @Field("id_mst_data_source") Integer idSource,
                                     @Field("updated_by") Integer userid);

    @FormUrlEncoded
    @POST("contact_collaborate_create")
    Call<RespPost> contactCollabCreate(@Field("id_cms_users") Integer userid,
                                       @Field("id_contact") Integer idcontact);

    @FormUrlEncoded
    @POST("contact_address_create")
    Call<ResponseBody> contactAddressCreate(@Field("id_contact") Integer idLead,
                                            @Field("address") String address,
                                            @Field("rt") String rt,
                                            @Field("rw") String rw,
                                            @Field("id_mst_address") Integer idAddress,
                                            @Field("id_mst_category_address") Integer idcategoryAddress,
                                            @Field("id_cms_users") Integer userid);

    @FormUrlEncoded
    @POST("contact_address_update")
    Call<ResponseBody> contactAddressUpdate(@Field("id") Integer id,
                                            @Field("id_contact") Integer idLead,
                                            @Field("address") String address,
                                            @Field("rt") String rt,
                                            @Field("rw") String rw,
                                            @Field("id_mst_address") Integer idAddress,
                                            @Field("id_mst_category_address") Integer idcategoryAddress,
                                            @Field("updated_by") Integer userid);


    @FormUrlEncoded
    @POST("contact_phone_create")
    Call<ResponseBody> contactPhoneCreate(@Field("id_contact") Integer idLead,
                                          @Field("number") String number,
                                          @Field("status") String status,
                                          @Field("id_cms_users") Integer userid);

    @FormUrlEncoded
    @POST("contact_phone_update")
    Call<ResponseBody> contactPhoneUpdate(@Field("id") Integer id,
                                          @Field("id_contact") Integer idContact,
                                          @Field("number") String number,
                                          @Field("status") String status,
                                          @Field("updated_by") Integer userid);


    @FormUrlEncoded
    @POST("contact_additional_create")
    Call<ResponseBody> contactAdditionalCreate(@Field("id_contact") Integer idLead,
                                               @Field("mother") String mother,
                                               @Field("family") Integer tanggungan,
                                               @Field("company") String company,
                                               @Field("position") String position,
                                               @Field("working_time") Integer lamakerja,
                                               @Field("income") Integer income,
                                               @Field("outlay") Integer outlay,
                                               @Field("id_contact_mst_status_place") Integer statusPlace,
                                               @Field("id_contact_mst_status_employee") Integer statusEmployee,
                                               @Field("id_cms_users") Integer userid);

    @FormUrlEncoded
    @POST("contact_additional_update")
    Call<ResponseBody> contactAdditionalUpdate(@Field("id") Integer id,
                                               @Field("id_contact") Integer idLead,
                                               @Field("mother") String mother,
                                               @Field("family") Integer tanggungan,
                                               @Field("id_contact_mst_status_place") Integer statusPlace,
                                               @Field("company") String company,
                                               @Field("id_contact_mst_status_employee") Integer statusEmployee,
                                               @Field("position") String position,
                                               @Field("working_time") Integer lamakerja,
                                               @Field("income") Integer income,
                                               @Field("outlay") Integer outlay,
                                               @Field("updated_by") Integer userid);


    @FormUrlEncoded
    @POST("contact_note_create")
    Call<ResponseBody> contactNoteCreate(@Field("id_contact") Integer idContact,
                                         @Field("id_cms_users") Integer idUser,
                                         @Field("note") String note);


    @FormUrlEncoded
    @POST("target_log_create")
    Call<ResponseBody> targetLog(@Field("id_target") Integer idLead,
                                 @Field("duration") String duration,
                                 @Field("id_mst_log_desc") Integer idDesc,
                                 @Field("recall") String recall,
                                 @Field("id_cms_users") Integer iduser);

    @GET("target_listing")
    Call<RespListTarget> listTarget(@Query("id_cms_users") Integer id,
                                    @Query("id_target_mst_status") Integer idStatus);

    @GET("target_listing")
    Call<RespListTarget> listTargetPagg(@Query("id_cms_users") Integer id,
                                        @Query("id_target_mst_status") Integer idStatus,
                                        @Query("limit") Integer limit,
                                        @Query("offset") Integer offset);

    @GET("target_listing")
    Call<RespListTarget> listTargetPaggWorking(@Query("id_cms_users") Integer id,
                                        @Query("id_target_mst_status") Integer idStatus);


    @GET("target_listing")
    Call<RespListTarget> listVisit(@Query("id_cms_users") Integer id,
                                        @Query("limit") Integer limit,
                                   @Query("offset") Integer offset);

    @GET("target_detail")
    Call<DetailTarget> targetDetail(@Query("id") Integer id);

    @GET("target_log_listing")
    Call<RespListLogTarget> listLogTarget(@Query("id_target") Integer id);

    @GET("target_note_listing")
    Call<RespListNote> listNoteTarget(@Query("id_target") Integer id);

    @GET("target_visum_listing")
    Call<RespListVisum> listVisum(@Query("id_target") Integer id);

    @FormUrlEncoded
    @POST("target_note_create")
    Call<ResponseBody> targetNoteCreate(@Field("id_target") Integer idTarget,
                                        @Field("id_cms_users") Integer idUser,
                                        @Field("note") String note);


    @FormUrlEncoded
    @POST("target_visum_create")
    Call<RespPost> targetVisumCreate(@Field("id_target") Integer idTarget,
                                         @Field("id_cms_users") Integer idUser,
                                         @Field("revisit") String revisit,
                                         @Field("id_mst_visum_status") Integer idVisum);

    @FormUrlEncoded
    @POST("target_update")
    Call<ResponseBody> targetUpdateStatus(@Field("id") Integer idTarget,
                                          @Field("id_target_mst_status") Integer idstatus,
                                          @Field("updated_by") Integer iduser);


    @FormUrlEncoded
    @POST("order_create")
    Call<RespPost> orderCreate(@Field("id_contact") Integer idContact,
                               @Field("id_cms_users") Integer idUser,
                               @Field("id_mst_outlet") Integer idOutlet,
                               @Field("id_mst_product") Integer idProduct,
                               @Field("id_order_mst_status") Integer idOrderStatus,
                               @Field("no_order") String noOrder,
                               @Field("id_mst_data_source") Integer idSource,
                               @Field("category") String category,
                               @Field("id_mst_cabang_fif") Integer idMstCabangFif,
                               @Field("status_address") String statusAddress,
                               @Field("survey") String survey);


    @FormUrlEncoded
    @POST("order_product_ufi_create")
    Call<ResponseBody> orderProductUfiCreate(@Field("id_order") Integer idOrder,
                                             @Field("id_mst_unit") Integer id_mst_unit,
                                             @Field("id_cms_users") Integer id_cms_users,
                                             @Field("nopol") String nopol,
                                             @Field("tax_status") String tax_status,
                                             @Field("owner") String owner);

    @FormUrlEncoded
    @POST("order_loan_create")
    Call<ResponseBody> orderLoanCreate(@Field("id_order") Integer idOrder,
                                       @Field("id_cms_users") Integer idUser,
                                       @Field("plafond") Integer plafond,
                                       @Field("down_payment") Integer dp,
                                       @Field("tenor") Integer tenor,
                                       @Field("need") String need,
                                       @Field("installment") Integer installment);

    @FormUrlEncoded
    @POST("order_surety_create")
    Call<ResponseBody> orderSuretyCreate(@Field("id_order") Integer idContact,
                                         @Field("id_cms_users") Integer idUser,
                                         @Field("name") String name,
                                         @Field("birth_place") String birth_place,
                                         @Field("birth_date") String birth_date,
                                         @Field("id_mst_job") Integer idMstJob,
                                         @Field("company") String company,
                                         @Field("id_contact_mst_status_employee") Integer idMstStatusEmployee,
                                         @Field("position") String position,
                                         @Field("working_time") Integer workingTime,
                                         @Field("income") Integer income,
                                         @Field("outlay") Integer outlay);


    @GET("order_listing_cfa")
    Call<RespListOrder> listOrder(@Query("id_cms_users") Integer iduser,
                                  @Query("id_order_mst_status") Integer idStatus,
                                  @Query("id_contact") Integer idContact);

    @GET("order_listing_cfa")
    Call<RespListOrder> listOrderPagg(@Query("id_cms_users") Integer iduser,
                                      @Query("id_order_mst_status") Integer idStatus,
                                      @Query("id_contact") Integer idContact,
                                      @Query("limit") Integer limit,
                                      @Query("offset") Integer offset);

    @GET("order_detail")
    Call<DetailOrder> orderDetail(@Query("id") Integer id);

    @GET("order_product_ufi_detail")
    Call<DetailProductUFI> productUfiDetail(@Query("id_order") Integer idorder);

    @GET("order_loan_listing")
    Call<DetailOrderLoan> orderLoanDetail(@Query("id_order") Integer idorder);

    @GET("order_surety_detail")
    Call<DetailOrderSurety> orderSuretyDetail(@Query("id_order") Integer idorder);

    //    Tidak Digunakan
    @GET("contact_detail_address")
    Call<DetailContactAddress> detailContactAddress(@Query("id_contact") Integer idContact);

    @GET("update_version")
    Call<DetailUpdateVersion> updateVersion(@Query("id") Integer id);

}
