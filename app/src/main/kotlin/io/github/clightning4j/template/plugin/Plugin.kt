package io.github.clightning4j.template.plugin

import jrpc.clightning.annotation.Hook
import jrpc.clightning.annotation.PluginOption
import jrpc.clightning.annotation.RPCMethod
import jrpc.clightning.annotation.Subscription
import jrpc.clightning.plugins.CLightningPlugin
import jrpc.clightning.plugins.log.PluginLog
import jrpc.service.converters.jsonwrapper.CLightningJsonObject

class Plugin : CLightningPlugin() {

    @PluginOption(
        name = "hello-kotlin",
        description = "This propriety is a fake propriety, there is any problem if it is not exist in the command line",
        defValue = "true",
        typeValue = "flag"
    )
    private var sayHello = false

    @RPCMethod(
        name = "say-hello",
        description = "Say hello from the Kotlin plugin",
        parameter = "[name]"
    )
    fun sayHello(plugin: CLightningPlugin, request: CLightningJsonObject, response: CLightningJsonObject) {
        val params = request["params"].asJsonArray
        plugin.log(PluginLog.DEBUG, "The request was: %s".format(request))
        if (params.size() == 0) {
            response.apply {
                add("answer", "Hello by Kotlin")
                add("warning", "The rpc method accept also a name parameter")
            }
        } else {
            val name = params[0]
            response.apply {
                add("answer", "Hello %s".format(name.asString))
            }
        }
    }

    @Subscription(notification = "invoice_creation")
    fun logInvoice(data: CLightningJsonObject) {
        log(PluginLog.DEBUG, "Subscription to 'invoice_creation' called")
    }

    @Hook(hook = "rpc_command")
    fun checkStopCommand(plugin: CLightningPlugin, request: CLightningJsonObject, response: CLightningJsonObject) {
        plugin.log(PluginLog.DEBUG, "Hook received %s".format(request))
        val params = request["params"].asJsonObject
        val rpcMethod = params["rpc_command"].asJsonObject["method"].asString
        if (rpcMethod == "stop") {
            plugin.log(PluginLog.INFO, "Hello by the kotlin plugin")
        }
        response.apply {
            add("result", "continue")
        }
    }
}
