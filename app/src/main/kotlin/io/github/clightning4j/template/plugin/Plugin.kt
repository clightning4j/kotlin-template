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
        val name = params[0];
        response.apply {
            add("answer", "Hello %s".format(name));
        }
    }

    @Subscription(notification = "invoice_creation")
    fun logInvoice(data: CLightningJsonObject) {
        log(PluginLog.DEBUG, "Subscription to 'invoice_creation' called")
    }

    @Hook(hook = "rpc_command")
    fun checkStopCommand(plugin: CLightningPlugin, request: CLightningJsonObject, response: CLightningJsonObject) {
        plugin.log(PluginLog.DEBUG, "Hook received %s".format(request))
        response.apply {
            add("result", "continue")
        }
    }
}
