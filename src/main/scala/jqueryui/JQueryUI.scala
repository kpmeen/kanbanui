package jqueryui

import org.scalajs.jquery._

import scala.scalajs.js

object JQueryUi {
  implicit def jquery2ui(jquery: JQuery): JQueryUi =
    jquery.asInstanceOf[JQueryUi]
}

trait JQueryUi extends JQuery {
  def draggable(options: js.Any): this.type = ???

  def droppable(options: js.Any): this.type = ???

  def sortable(options: js.Any): this.type = ???

  def position(options: js.Any): this.type = ???
}

trait JQueryUiObject extends js.Object {
  var helper: JQueryUi = _
  var position: JQueryUiCoordinate = _
  var offset: JQueryUiCoordinate = _
}

trait JQueryUiDropObject extends JQueryUiObject {
  var draggable: JQuery = _
}

trait JQueryUiCoordinate extends js.Object {
  var left: js.Number = _
  var top: js.Number = _
}