"use client";
import { Typography } from "@mui/material";
import { Box, Grid } from "@mui/system";
import Link from "@next/link";
import { useEffect } from "react";
import { useUserStore } from "@stores/userStores";

export function Profile() {
  const { user } = useUserStore();

  useEffect(() => {
    const storedUser = localStorage.getItem("user");

    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      useUserStore.setState({ user: parsedUser });
    }
  }, []);

  return (
    <Box className="p-6 max-w-2xl mx-auto">
      <Typography variant="h4" className="mb-6">
        Perfil
      </Typography>
      <Grid>
        <Box className="space-y-4">
          <Typography>
            <strong>Nome:</strong> {user.firstName} {user.lastName}
          </Typography>
          <Typography>
            <strong>Email:</strong> {user.email}
          </Typography>
          <Typography>
            <strong>Telefone:</strong> {user.cellPhoneNumber}
          </Typography>
          <Typography>
            <strong>CPF:</strong> {user.cpf}
          </Typography>
          <Typography>
            <strong>Data de Nascimento:</strong> {user.birthDate}
          </Typography>
        </Box>
      </Grid>
      <Link
        href="/profile/edit"
        className="inline-block mt-6 text-blue-600 hover:underline"
      >
        Editar Perfil
      </Link>
    </Box>
  );
}

export default Profile;
