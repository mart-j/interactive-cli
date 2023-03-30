package update

import tui.TerminalApp.Step
import tui.view.KeyEvent
import tui.{TerminalApp, TerminalEvent}
import update.state.CliState

object InputHandler {
  def updateState(
      state: CliState,
      event: TerminalEvent[String]
  ): TerminalApp.Step[CliState, Unit] =
    event match {
      case TerminalEvent.SystemEvent(keyEvent) =>
        if (state.textInput.focused) {
          keyEvent match {
            case KeyEvent.Character(char) => Step.update(state.onKeyPress(char))
            case KeyEvent.Exit            => Step.update(state.exitInputMode)
            case KeyEvent.Delete          => Step.update(state.onKeyDelPress)
            case KeyEvent.Enter           => Step.update(state.newTodo)
            case KeyEvent.Left            => Step.update(state.moveCursorLeft)
            case KeyEvent.Right           => Step.update(state.moveCursorRight)
            case _                        => Step.update(state)
          }
        } else
          keyEvent match {
            case KeyEvent.Character(' ') =>
              Step.update(state.toggle)
            case KeyEvent.Character('a') =>
              Step.update(state.toggleAll)
            case KeyEvent.Character('D') =>
              Step.update(state.delete)
            case KeyEvent.Character('I') =>
              Step.update(state.enterInputMode)
            case KeyEvent.Up | KeyEvent.Character('k') =>
              Step.update(state.moveUp)
            case KeyEvent.Down | KeyEvent.Character('j') =>
              Step.update(state.moveDown)
            case KeyEvent.Escape | KeyEvent.Exit | KeyEvent.Character('q') =>
              Step.succeed()
            case _ =>
              Step.update(state)
          }

      case TerminalEvent.UserEvent(value) =>
        Step.update(state.copy(todos = state.todos ++ Seq(value)))
    }

}
