"use client";
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
} from "@mui/material";
import { ApiService } from "@services/ApiService";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export function SignInForms() {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [errors, setErrors] = useState({
    email: "",
    password: "",
  });

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
    <>
      <Grid
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
        }}
      >
        <Card
          color="primary"
          variant="elevation"
          style={{ textAlign: "center" }}
        >
          <Typography style={{ margin: "center", fontSize: 25 }}>
            Sign In
          </Typography>
          <FormControl sx={{ m: 1, width: "50ch" }}>
            <TextField
              id="email"
              label="Email"
              variant="outlined"
              required
              size="small"
              margin="dense"
              onChange={(e) => setUserEmail(e.target.value)}
              error={errors.email !== ""}
              helperText={errors.email}
            />
            <TextField
              id="password"
              label="Password"
              type={showPassword ? "text" : "password"}
              variant="outlined"
              required
              size="small"
              margin="dense"
              onChange={(e) => setUserPassword(e.target.value)}
              error={errors.password !== ""}
              helperText={errors.password}
              InputProps={{
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
            />
            <Button variant="contained" onClick={handleSubmit}>
              Sign In
            </Button>
          </FormControl>
        </Card>
      </Grid>
    </>
  );
}

export default SignInForms;
