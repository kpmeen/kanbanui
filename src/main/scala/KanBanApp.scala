/**
 * Copyright(c) 2014 Knut Petter Meen, all rights reserved.
 */

import jqueryui.JQueryUi._
import jqueryui._
import org.scalajs.jquery.{JQueryEventObject, jQuery}

import scala.scalajs.js.Dynamic.literal
import scala.scalajs.js.JSApp
import scala.util.Random
import scalatags.JsDom.all._

/**
 * The KanBan sample application
 */
object KanBanApp extends JSApp {

  def main(): Unit = {
    jQuery("body").append(h4("ScalaJS - KanBan Board").render)
    jQuery(Board.initializeBoard _)
  }
}

/**
 * KanBan board container
 */
object Board {

  /**
   * Initialize the KanBan board
   */
  def initializeBoard(): Unit = {
    val body = jQuery("body")

    // The menu button-row...
    body.append(addMenuButtons().render)

    // The KanBan board container...
    body.append(
      div(id := "board", cls := "container-fluid")(
        div(cls := "board-column-container")
      ).render
    )

    jQuery(".board-column-container").droppable(literal(
      accept = ".board-outer-column",
      drop = (event: JQueryEventObject, ui: JQueryUiDropObject) => {
        ui.draggable.detach().appendTo(jQuery(event.target))
      }
    )).sortable(literal(
      connectWith = ".board-column-container",
      handle = ".board-header"
    ))

    body.append(Card.addCardModal().render)
    body.append(Column.addColumnModal().render)

    initializeListeners()
  }

  /**
   * Prepare the menu buttons
   */
  def addMenuButtons() = {
    div(id := "buttonrow")(
      button(
        id := "addcolumn",
        cls := "btn btn-primary",
        "data-toggle".attr := "modal",
        "data-target".attr := "#addColumnModal"
      )("Add Column"),
      button(
        id := "addcard",
        cls := "btn btn-success",
        "data-toggle".attr := "modal",
        "data-target".attr := "#addCardModal"
      )("Add Card")
    )
  }

  /**
   * Initializes the action listeners used on the board
   */
  private def initializeListeners(): Unit = {
    jQuery("#addCardSave").click(Card.createCard _)
    jQuery("#addColumnSave").click(Column.appendColumn _)
  }
}

/**
 * Column related functions
 */
object Column {

  /**
   * Modal dialogue for creating a new Column.
   */
  def addColumnModal() = {
    div(
      id := "addColumnModal",
      cls := "modal fade",
      tabindex := "-1",
      role := "dialog",
      "aria-labelledby".attr := "addColumnLabel",
      "aria-hidden".attr := "true"
    )(div(cls := "modal-dialog")(
      div(cls := "modal-content")(
        div(cls := "modal-header")(
          button(`type` := "button", cls := "close", "data-dismiss".attr := "modal", "aria-label".attr := "Close")(
            span("aria-hidden".attr := "true")("x")
          ),
          h4(cls := "modal-title", id := "addColumnLabel")("Add Column")
        ),
        div(cls := "modal-body")(
          label(`for` := "columnTitle")("Title"),
          input(id := "columnTitle", `type` := "text", cls := "form-control", placeholder := "Column Title...")
        ),
        div(cls := "modal-footer")(
          button(`type` := "button", cls := "btn btn-default", "data-dismiss".attr := "modal")("Close"),
          button(id := "addColumnSave", `type` := "button", "data-dismiss".attr := "modal", cls := "btn btn-primary")("Save")
        )
      )
    ))
  }

  /**
   * Appends a new column to the Board
   */
  def appendColumn(): Unit = {
    val title = jQuery("#columnTitle")
    val colId = Random.alphanumeric.take(4).mkString

    jQuery(".board-column-container").append(
      div(cls := "board-outer-column col-md-2")(
        div(id := s"headerContainer-$colId", cls := "row board-header")(
          div(h3(title.value().toString))
        ),
        div(id := s"columnContainer-$colId", cls := "row board")(
          div(id := colId, cls := "board-column")
        )
      ).render
    )

    jQuery(s"#$colId").droppable(literal(
      accept = ".task",
      drop = (event: JQueryEventObject, ui: JQueryUiDropObject) => {
        ui.draggable.detach().appendTo(jQuery(event.target))
      }
    )).sortable(literal(
      connectWith = ".board-column",
      handle = ".task-title"
    ))

    title.value("")
  }

}

/**
 * Card related functions
 */
object Card {

  /**
   * Modal dialogue for creating a new Card.
   */
  def addCardModal() = {
    div(
      id := "addCardModal",
      cls := "modal fade",
      tabindex := "-1",
      role := "dialog",
      "aria-labelledby".attr := "addCardLabel",
      "aria-hidden".attr := "true"
    )(div(cls := "modal-dialog")(
      div(cls := "modal-content")(
        div(cls := "modal-header")(
          button(`type` := "button", cls := "close", "data-dismiss".attr := "modal", "aria-label".attr := "Close")(
            span("aria-hidden".attr := "true")("x")
          ),
          h4(cls := "modal-title", id := "addCardLabel")("Create Task")
        ),
        div(cls := "modal-body")(
          label(`for` := "taskTitle")("Title"),
          input(id := "taskTitle", `type` := "text", cls := "form-control", placeholder := "Task Title..."),
          label(`for` := "taskDesc")("Description"),
          textarea(id := "taskDesc", cls := "form-control", rows := 3)
        ),
        div(cls := "modal-footer")(
          button(`type` := "button", cls := "btn btn-default", "data-dismiss".attr := "modal")("Close"),
          button(id := "addCardSave", `type` := "button", "data-dismiss".attr := "modal", cls := "btn btn-primary")("Save")
        )
      )
    ))
  }

  /**
   * Adds a new Card to the first column of the Board.
   */
  def createCard(): Unit = {
    val title = jQuery("#taskTitle")
    val desc = jQuery("#taskDesc")
    val cid = Random.alphanumeric.take(4).mkString

    val card = div(id := cid, cls := "panel panel-info task")(
      div(cls := "panel-heading task-title")(
        h3(cls := "panel-title")(title.value().toString)
      ),
      div(cls := "panel-body")(desc.value().toString)
    ).render

    jQuery(".board-outer-column").first().find(".board-column").append(card)

    title.value("")
    desc.value("")
  }

}