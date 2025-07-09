"use client";
import { AppContext } from "@components/context/AppContext";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import {
  Button,
  Card,
  FormControl,
  Grid,
  IconButton,
  InputAdornment,
  TextField,
  Typography,
  Box,
} from "@mui/material";
import { ApiService } from "@services/ApiService";
import { useRouter } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import EmailIcon from "@mui/icons-material/Email";
import HttpsIcon from "@mui/icons-material/Https";

export function SignInForms() {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [errors, setErrors] = useState({
    email: "",
    password: "",
  });
  const { setAuth, setBtnColor, setBgColor } = useContext(AppContext);

  const route = useRouter();

  const [showPassword, setShowPassword] = useState(false);

  const handleSubmit = async () => {
    if (errors.email === "" && errors.password === "") {
      const response = await ApiService.signIn({
        email: userEmail,
        password: userPassword,
      });

      if (response && response.status === 201) {
        localStorage.setItem("token", response.data);
        ApiService.getUserInfos({ email: userEmail }).then((res) => {
          if (res) {
            localStorage.setItem("user", JSON.stringify(res.data));
          }
        });
        ApiService.getPreferences().then((res) => {
          if (res) {
            localStorage.setItem("prefs", JSON.stringify(res.data));
            setBtnColor(res.data.btnColor);
            setBgColor(res.data.bgColor);
          }
        });
        setAuth(true);
        route.push("/homepage");
      }
    }
  };

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    event.preventDefault();
  };

  const handleMouseUpPassword = (
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    event.preventDefault();
  };

  useEffect(() => {
    setErrors((prev) => ({
      ...prev,
      email: userEmail.length === 0 ? "Email cannot be empty" : "",
      password: userPassword.length === 0 ? "Password cannot be empty" : "",
    }));
  }, [userEmail, userPassword]);

  return (
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      sx={{ height: "100vh" }}
    >
      <Card
        elevation={3}
        sx={{
          padding: 4,
          minWidth: 400,
          maxWidth: 450,
        }}
      >
        <Box mb={2}>
          <Typography variant="h5" align="center">
            Sign In
          </Typography>
        </Box>
        <FormControl fullWidth>
          <TextField
            id="email"
            label="Email"
            variant="outlined"
            required
            onChange={(e) => setUserEmail(e.target.value)}
            error={errors.email !== ""}
            helperText={errors.email}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <EmailIcon />
                </InputAdornment>
              ),
            }}
            sx={{ mb: 2 }}
          />
          <TextField
            id="password"
            label="Password"
            type={showPassword ? "text" : "password"}
            variant="outlined"
            required
            onChange={(e) => setUserPassword(e.target.value)}
            error={errors.password !== ""}
            helperText={errors.password}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <HttpsIcon />
                </InputAdornment>
              ),
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={handleClickShowPassword}
                    onMouseDown={handleMouseDownPassword}
                    onMouseUp={handleMouseUpPassword}
                  >
                    {showPassword ? <Visibility /> : <VisibilityOff />}
                  </IconButton>
                </InputAdornment>
              ),
            }}
            sx={{ mb: 2 }}
          />
          <Button
            variant="contained"
            onClick={handleSubmit}
            fullWidth
            sx={{ mt: 1 }}
          >
            Sign In
          </Button>
        </FormControl>
      </Card>
    </Grid>
  );
}

export default SignInForms;
