package com.lmu.warungdana.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gigabyte on 11/28/2018.
 */

public class ListCabangFif {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code_cab_fif")
    @Expose
    private Integer codeCabFif;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("code_pos_fif")
    @Expose
    private Integer codePosFif;
    @SerializedName("pos_name")
    @Expose
    private String posName;
    @SerializedName("id_cms_users")
    @Expose
    private Integer idCmsUsers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodeCabFif() {
        return codeCabFif;
    }

    public void setCodeCabFif(Integer codeCabFif) {
        this.codeCabFif = codeCabFif;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getCodePosFif() {
        return codePosFif;
    }

    public void setCodePosFif(Integer codePosFif) {
        this.codePosFif = codePosFif;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public Integer getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(Integer idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

}
