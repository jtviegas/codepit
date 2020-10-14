import logging.config
import os

from dependency_injector import containers, providers
from computation.strategy.strategy import Strategy

CONFIG_ENV_VAR = 'CONFIG'
DEFAULT_CONFIG = 'config.ini'


class Injector(containers.DeclarativeContainer):

    config = providers.Configuration()

    config_file = DEFAULT_CONFIG
    if CONFIG_ENV_VAR in os.environ.keys():
        config_file = os.environ[CONFIG_ENV_VAR]

    print('using config: {}'.format(config_file))
    config.from_ini(config_file)

    configure_logging = providers.Callable(
        logging.config.fileConfig,
        fname=config.logging()['config'],
    )

    strategy = providers.Singleton(
        Strategy.factory,
        config,
    )

