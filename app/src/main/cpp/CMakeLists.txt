cmake_minimum_required(VERSION 3.4.1)

add_library(
    get-column-names SHARED
    get-column-names.c
)

target_link_libraries(
    get-column-names
    android
    log
)
