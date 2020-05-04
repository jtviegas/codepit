#' install package if missing
#'
#' This function allows you to install a package if it hasn't been installed yet.
#' @param pkg the package name as string
#' @keywords install package
#' @export
#' @examples
#' install_if_missing("roxygen2")
install_if_missing <- function(p) {
  if (p %in% rownames(installed.packages()) == FALSE) {
    install.packages(p, dependencies = TRUE)
  }
  else {
    cat(paste("Skipping already installed package:", p, "\n"))
  }
}
