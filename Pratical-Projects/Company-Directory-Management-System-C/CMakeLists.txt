cmake_minimum_required(VERSION 3.25)

project(LP_Projeto C)

set(CMAKE_C_STANDARD 23)

include_directories(inc)
add_subdirectory(src)

add_executable(LP_Projeto
        src/main.c
        src/MainMenu.c
        src/Output.c src/Input.c

        src/admin/AdminMenu.c
        src/admin/ManageCatalog.c
        src/admin/ManageBusiness.c
        src/admin/ManageReport.c

        src/user/UserMenu.c
        src/user/SubmitSearch.c
        src/user/SubmitComment.c
        src/user/SubmitRating.c

        src/company/CompanyMenu.c
        src/company/AccessCompany.c
        src/company/AccessComment.c
        src/company/AccessReport.c
)