package update.state

final case class CliState(
  todos: Seq[String] = Seq.empty,
  cursorIndex: Int = 0,
  selected: Set[Int] = Set.empty,
  textInput: TextInput = TextInput.Empty
) {

  def toggle: CliState = {
    val newSelected =
      if (selected(cursorIndex)) selected - cursorIndex
      else selected + cursorIndex
    copy(selected = newSelected)
  }

  def toggleAll: CliState = {
    val newSelected =
      if (selected.isEmpty) todos.indices.toSet
      else Set.empty[Int]
    copy(selected = newSelected)
  }

  def onKeyPress(char: Char): CliState =
    copy(textInput = textInput.add(char))

  def onKeyDelPress: CliState =
    copy(textInput = textInput.delete)

  def newTodo: CliState =
    copy(todos = todos :+ textInput.value, textInput = textInput.clearBlur)

  def moveCursorLeft: CliState =
    copy(textInput = textInput.cursorLeft)

  def moveCursorRight: CliState =
    copy(textInput = textInput.cursorRight)

  def exitInputMode: CliState =
    copy(textInput = textInput.clearBlur)

  def enterInputMode: CliState =
    copy(textInput = textInput.focus)

  def delete: CliState = {
    val newState = todos.zipWithIndex.filter { case (_, i) => i != cursorIndex }.map(_._1)
    copy(todos = newState)
  }

  def moveUp: CliState =
    if (cursorIndex == 0) this
    else copy(cursorIndex = cursorIndex - 1)

  def moveDown: CliState =
    if (cursorIndex == todos.size - 1) this
    else copy(cursorIndex = cursorIndex + 1)

}
