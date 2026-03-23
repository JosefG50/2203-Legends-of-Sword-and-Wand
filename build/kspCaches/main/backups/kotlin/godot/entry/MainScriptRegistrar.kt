// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
package godot.entry

import godot.MainScript
import godot.`annotation`.RegisteredClassMetadata
import godot.api.MultiplayerAPI.RPCMode.DISABLED
import godot.api.MultiplayerPeer.TransferMode.RELIABLE
import godot.core.KtConstructor0
import godot.core.KtRpcConfig
import godot.core.VariantParser.NIL
import godot.registration.ClassRegistrar
import godot.registration.ClassRegistry
import godot.registration.KtFunctionArgument
import kotlin.Unit
import kotlin.collections.listOf

@RegisteredClassMetadata(
  "MainScript",
  "Node",
  "godot.MainScript",
  "src/main/kotlin/godot/MainScript.kt",
  "gdj/godot/MainScript.gdj",
  "2203-Legends-of-Sword-and-Wand",
  "godot.api.Node,godot.api.Object,godot.core.KtObject,godot.common.interop.NativeWrapper,godot.common.interop.NativePointer,kotlin.Any",
  "",
  "",
  "godot.MainScript._ready",
  true,
)
public open class MainScriptRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<MainScript>(listOf(), MainScript::class, false, "Node", "MainScript", "src/main/kotlin/godot/MainScript.kt", "gdj/godot/MainScript.gdj") {
        constructor(KtConstructor0(::MainScript))
        notificationFunctions(listOf())
        function(MainScript::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), KtRpcConfig(DISABLED.id.toInt(), false, RELIABLE.id.toInt(), 0))
      }
    }
  }
}
