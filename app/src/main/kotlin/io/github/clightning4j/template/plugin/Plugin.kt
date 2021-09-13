package io.github.clightning4j.template.plugin

import jrpc.clightning.CLightningRPC
import jrpc.clightning.annotation.Hook
import jrpc.clightning.annotation.PluginOption
import jrpc.clightning.annotation.RPCMethod
import jrpc.clightning.annotation.Subscription
import jrpc.clightning.plugins.CLightningPlugin
import jrpc.clightning.plugins.ICLightningPlugin
import jrpc.clightning.plugins.log.PluginLog
import jrpc.service.converters.jsonwrapper.CLightningJsonObject
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

// Extend the abstract class CLightningPlugin to use all the stuff
// to implement the plugin.
// Se also the java doc at the following link https://clightning4j.github.io/JRPClightning/
class Plugin : CLightningPlugin() {

    // all the propriety with the PluginOption annotation
    // are bind from the library jrpclightning.
    // so, you don't need to made any other check on it
    @PluginOption(
        name = "hello-kotlin",
        description = "This propriety is a fake propriety, there is any problem if it is not exist in the command line",
        defValue = "true",
        typeValue = "flag"
    )
    private var sayHello = false

    // This method is called by the library, when the plugin finish to set up all the stuff with
    // c-lightning, at this point this method is called
    override fun onInit(plugin: ICLightningPlugin, request: CLightningJsonObject, response: CLightningJsonObject) {
        super.onInit(plugin, request, response)
        // if you need to call some RPC method, you need to wrap the call inside a therea
        Executors.newSingleThreadScheduledExecutor().schedule(
            {
                var getInfo = CLightningRPC.getInstance().info
                plugin.log(PluginLog.INFO, "Message from the template plugin %s".format(getInfo.id))
                plugin.log(PluginLog.INFO, "Form more information visit the doc: https://clightning4j.github.io/JRPClightning/")
            },
            10, TimeUnit.SECONDS
        )
    }

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
