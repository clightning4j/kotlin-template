package io.github.clightning4j.template

import io.github.clightning4j.template.plugin.Plugin

fun main() {
    // Starting point of your plugin!
    val plugin = Plugin()
    // start to listen c-lightning calls
    plugin.start()
}
