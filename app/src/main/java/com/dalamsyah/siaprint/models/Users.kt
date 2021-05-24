package com.dalamsyah.siaprint.models

import com.google.gson.annotations.SerializedName

data class Users (
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password_hash")
    var password_hash: String? = null,

    @SerializedName("reset_hash")
    var reset_hash: String? = null,

    @SerializedName("reset_at")
    var reset_at: String? = null,

    @SerializedName("reset_expires")
    var reset_expires: String? = null,

    @SerializedName("activate_hash")
    var activate_hash: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("status_message")
    var status_message: String? = null,

    @SerializedName("active")
    var active: String? = null,

    @SerializedName("force_pass_reset")
    var force_pass_reset: String? = null,

    @SerializedName("created_at")
    var created_at: String? = null,

    @SerializedName("updated_at")
    var updated_at: String? = null,

    @SerializedName("deleted_at")
    var deleted_at: String? = null,

    @SerializedName("balance")
    var balance: String? = null,

    @SerializedName("phone")
    var phone: String? = null,

    @SerializedName("first_name")
    var first_name: String? = null,

    @SerializedName("last_name")
    var last_name: String? = null,

    @SerializedName("admin_comp")
    var admin_comp: String? = null,

    @SerializedName("owner")
    var owner: String? = null
)
