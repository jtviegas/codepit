from computation.strategy.strategy import Strategy


class Double(Strategy):

    def __init__(self) -> None:
        super().__init__()

    def compute(self, value):
        self.logger.debug('...compute %s...', value)
        return 2 * value