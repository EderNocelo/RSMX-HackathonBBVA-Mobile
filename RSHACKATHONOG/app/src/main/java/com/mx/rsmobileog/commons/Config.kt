package com.mx.rsmobileog.commons

object Config {
    /**
     * Dev: 192.168.83.22
     * Loc: 192.168.100.22
     * Prod:
     * */

    // INIT URL API
    val SERVER = "http://192.168.83.22"
    val PORT = ":8071"

    // INIT URL API
    val SERVER_API = SERVER + PORT

    // INIT IMAGES DIR
    val SERVER_MAILBOX_IMAGES = "${SERVER}/sicoms_apps_resources/mailbox/images/"
    val SERVER_IMAGES_MSG_INST = "${SERVER}/uploads/msg_ins/"
}