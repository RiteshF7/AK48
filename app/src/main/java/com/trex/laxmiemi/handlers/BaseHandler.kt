package com.trex.laxmiemi.handlers

import com.trex.rexnetwork.data.ActionMessageDTO

interface IActionHandler {
    fun handle(messageDTO: ActionMessageDTO)
}
abstract class BaseHandler : IActionHandler{

}
