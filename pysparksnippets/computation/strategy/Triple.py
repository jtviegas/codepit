from computation.strategy.strategy import Strategy


class Triple(Strategy):

    def compute(self, value):
        self.logger.debug('...compute %s...', value)
        return 3 * value


