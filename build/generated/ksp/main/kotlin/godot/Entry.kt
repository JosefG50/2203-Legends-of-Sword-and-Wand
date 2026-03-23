// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
package godot.entry.LmilWpJyTJHgwGdQLAvw

import godot.MainScript
import godot.entry.MainScriptRegistrar
import godot.registerEngineTypeMethods
import godot.registerEngineTypes
import godot.registerVariantMapping
import godot.registration.Entry
import godot.registration.Entry.Context
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.listOf
import kotlin.reflect.KClass

public class Entry : Entry() {
  public override val classRegistrarCount: Int = 1

  public override val projectName: String = "2203-Legends-of-Sword-and-Wand"

  public override fun Context.`init`(): Unit {
    MainScriptRegistrar().register(registry)
  }

  public override fun Context.initEngineTypes(): Unit {
    registerVariantMapping()
    registerEngineTypes()
    registerEngineTypeMethods()
  }

  public override fun Context.getRegisteredClasses(): List<KClass<*>> = listOf(MainScript::class)
}
