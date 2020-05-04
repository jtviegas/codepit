#' detach package
#'
#' This function allows you to detach/unload a package that has been loaded.
#' @param pkg the package name as string
#' @keywords detach package unload
#' @export
#' @examples
#' detach_package("roxygen2")
detach_package <- function(pkg) {
  search_item <- paste("package", pkg, sep = ":")
  while(search_item %in% search())
  {
    detach(search_item, unload = TRUE, character.only = TRUE)
  }
}