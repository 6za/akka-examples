package tech.code

sealed trait Action

case class DockerCompose(filter: String, strategy: String) extends Action
