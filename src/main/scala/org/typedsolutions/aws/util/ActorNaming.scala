package org.typedsolutions.aws.util

import java.util.UUID

object ActorNaming {
  private val upperCasedChars = "([A-Z]+)".r

  def name[T : Manifest]: String = {
    val simpleName = implicitly[Manifest[T]].runtimeClass.getSimpleName
    upperCasedChars.replaceAllIn(simpleName, m => {
      val lowerCase = m.group(1).toLowerCase
      if (m.start == 0) lowerCase else s"-$lowerCase"
    })
  }

  def uniqueName[T: Manifest]: String = {
    s"${name[T]}-${uuid()}"
  }

  private def uuid(): UUID = UUID.randomUUID()
}
