package update.state

import tui.view.View
import zio.Chunk

case class TextInput(
  value: String,
  focused: Boolean = false,
  cursor: Int = -1
) {

  def add(char: Char): TextInput =
    if (cursor == -1) copy(value + char)
    else {
      val str = value.substring(0, cursor) + char + value.substring(cursor, length)
      copy(value = str, cursor = cursor + 1)
    }

  def delete: TextInput =
    this match {
      case TextInput(value, _, -1) => copy(value = value.dropRight(1))
      case TextInput(_, _, 0)      => this
      case TextInput(value, _, cursor) =>
        val str = value.substring(0, cursor - 1) + value.substring(cursor, length)
        this.copy(value = str, cursor = cursor - 1)
    }

  def cursorLeft: TextInput =
    cursor match {
      case 0  => this
      case -1 => copy(cursor = if (value.isEmpty) -1 else length - 1)
      case n  => copy(cursor = n - 1)
    }

  def cursorRight: TextInput =
    cursor match {
      case -1                   => this
      case n if n == length - 1 => copy(cursor = -1)
      case n                    => copy(cursor = n + 1)
    }

  def clearBlur: TextInput = TextInput("")

  def focus: TextInput = copy(focused = true)

  def length: Int = value.length

  def toView: View =
    View.horizontal(0)(
      Option
        .when(focused) {
          if (cursor < 0) Seq(View.text(value), View.text(" ").inverted)
          else
            Chunk(
              View.text(value.substring(0, cursor)),
              View.text(value(cursor).toString).inverted,
              View.text(value.substring(cursor + 1, value.length))
            )
        }
        .toList
        .flatten: _*
    )
}

object TextInput {
  val Empty: TextInput = TextInput("")
}
