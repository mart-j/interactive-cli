package update

import tui.TUI
import update.state.CliState
import zio._

object Main extends ZIOAppDefault {
  val run = {
    for {
      result <- CliApp.run(CliState()).provide(TUI.live(false))
      _ <- ZIO.debug(result.toString)
      _ <- CliApp.run(CliState()).provide(TUI.live(false))
    } yield ()
  }
}
