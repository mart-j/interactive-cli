package update

import tui._
import update.state.CliState
import view._
import zio._
import zio.stream.ZStream

trait CliApp extends TerminalApp[String, CliState, Unit]

object CliApp extends TerminalApp[String, CliState, Unit] {
  val stream: ZStream[Any, Throwable, String] =
    ZStream.tick(5.seconds).mapZIO(_ => Clock.instant.map(_.toString))

  override def runOption(initialState: CliState): RIO[TUI, Option[Unit]] =
    TUI.runWithEvents(this)(stream, initialState)

  override def render(state: CliState): View = {
    val todos = state.todos.zipWithIndex.map { case (todo, idx) =>
      val selected: View =
        if (state.selected.contains(idx)) View.text("▣").green
        else View.text("☐").cyan.dim

      val isActive = idx == state.cursorIndex

      val cursor: View =
        if (isActive) View.text("❯").cyan
        else View.text(" ")

      View.horizontal(1, VerticalAlignment.top)(
        View.horizontal(0)(cursor, selected),
        View.vertical(View.text(todo))
      )
    }

    View
      .vertical(
        Seq(
          if (state.textInput.focused) View.text("NEW TODO").blue
          else View.text("TODOS").blue,
          View.text("────────────────────────").blue.dim
        ) ++
          todos :+
          state.textInput.toView: _*
      )
      .padding(1)
  }

  override def update(
      state: CliState,
      event: TerminalEvent[String]
  ): TerminalApp.Step[CliState, Unit] =
    InputHandler.updateState(state, event)
}
