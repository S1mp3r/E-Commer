"use client";
import React, { useContext, useEffect, useState } from "react";
import { AppContext } from "@components/context/AppContext";
import MenuIcon from "@mui/icons-material/Menu";
import {
  AppBar,
  Avatar,
  Box,
  Button,
  Container,
  Grid,
  IconButton,
  Menu,
  MenuItem,
  SvgIcon,
  Toolbar,
  Tooltip,
  Typography,
} from "@mui/material";
import Page from "./forms";
import { ApiService } from "@services/ApiService";
import { useRouter } from "@next/navigation";
import { User } from "src/interfaces/User";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import LogoutIcon from "@mui/icons-material/Logout";
import DashboardIcon from "@mui/icons-material/Dashboard";

const pages = ["Products", "Pricing", "Blog"];
const settings = ["Profile", "Dashboard", "Logout"];

export function GlobalPage() {
  const { isLoading } = useContext(AppContext);
  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(
    null
  );
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(
    null
  );
  const { auth, setAuth } = useContext(AppContext);
  const [token, setToken] = useState<string | null>(null);
  const [userSession, setUserSession] = useState<User>(null);
  const route = useRouter();

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  const handleLogout = async () => {
    await ApiService.logout();
    handleCloseUserMenu();
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    route.push("/signin");
  };

  const handleProfile = () => {
    handleCloseUserMenu();
    route.push("/profile");
  };

  useEffect(() => {
    const storedToken = localStorage.getItem("token");
    const storedUser = localStorage.getItem("user");

    if (storedToken) setToken(storedToken);
    if (storedUser) setUserSession(JSON.parse(storedUser));
  }, []);

  useEffect(() => {
    if (token === null) {
      setAuth(false);
    } else {
      setAuth(true);
    }
  }, [token]);

  // if (isLoading) {
  //   return (
  //     <div className="fixed top-0 w-full bg-gray-100 text-center">
  //       Loading...
  //     </div>
  //   );
  // }

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Box sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: "none", md: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            LOGO
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{ display: { xs: "block", md: "none" } }}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={handleCloseNavMenu}>
                  <Typography sx={{ textAlign: "center" }}>{page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          {/* <SvgIcon sx={{ display: { xs: "flex", md: "none" }, mr: 1 }} /> */}
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: "flex", md: "none" },
              flexGrow: 1,
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            LOGO
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            {pages.map((page) => (
              <Button
                key={page}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: "white", display: "block" }}
              >
                {page}
              </Button>
            ))}
          </Box>
          {auth ? (
            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title="Open Settings">
                <IconButton
                  onClick={handleOpenUserMenu}
                  sx={{ p: 0 }}
                  className="hover:cursor-pointer"
                >
                  <Typography sx={{ mr: 1 }} color="white">
                    Hi,{" "}
                    {userSession && userSession.firstName
                      ? userSession.firstName
                      : "Guest"}
                    !
                  </Typography>
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: "45px" }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                {settings.map((setting) =>
                  setting === "Logout" ? (
                    <MenuItem key={setting} onClick={() => handleLogout()}>
                      <Grid container>
                        <LogoutIcon color="error" />
                        <Typography sx={{ textAlign: "center" }} color="error">
                          {setting}
                        </Typography>
                      </Grid>
                    </MenuItem>
                  ) : setting === "Profile" ? (
                    <MenuItem key={setting} onClick={() => handleProfile()}>
                      <Grid container>
                        <AccountCircleIcon />
                        <Typography sx={{ textAlign: "center" }}>
                          {setting}
                        </Typography>
                      </Grid>
                    </MenuItem>
                  ) : (
                    <MenuItem key={setting} onClick={handleCloseUserMenu}>
                      <Grid container>
                        <DashboardIcon />
                        <Typography sx={{ textAlign: "center" }}>
                          {setting}
                        </Typography>
                      </Grid>
                    </MenuItem>
                  )
                )}
              </Menu>
            </Box>
          ) : (
            <Page></Page>
          )}
        </Toolbar>
      </Container>
    </AppBar>
  );
}
