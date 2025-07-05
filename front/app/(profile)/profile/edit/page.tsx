"use client";
import { Box, TextField, Button, Typography } from "@mui/material";
import { useUserStore } from "@stores/userStores";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { ApiService } from "@services/ApiService";

export default function EditProfilePage() {
  const { user, setUser } = useUserStore();
  const [form, setForm] = useState(user);
  const router = useRouter();

  const handleChange = (field: keyof typeof form, value: string) => {
    setForm({ ...form, [field]: value });
  };

  const handleSubmit = () => {
    setUser(form);
    ApiService.updateUser({ user: form });
    router.push("/profile");
  };

  return (
    <Box>
      <Box className="p-6 max-w-2xl mx-auto space-y-4">
        <Typography variant="h4" className="mb-6">
          Edit Profile
        </Typography>
        <TextField
          label="Nome"
          // fullWidth
          value={form.firstName}
          onChange={(e) => handleChange("firstName", e.target.value)}
        />
        <br></br>
        <TextField
          label="Sobrenome"
          // fullWidth
          value={form.lastName}
          onChange={(e) => handleChange("lastName", e.target.value)}
        />
        <br></br>
        <TextField
          label="Email"
          // fullWidth
          value={form.email}
          onChange={(e) => handleChange("email", e.target.value)}
        />
        <br></br>
        <TextField
          label="Telefone"
          // fullWidth
          value={form.cellPhoneNumber}
          onChange={(e) => handleChange("cellPhoneNumber", e.target.value)}
        />
        <br></br>
        <TextField
          label="CPF"
          // fullWidth
          value={form.cpf}
          onChange={(e) => handleChange("cpf", e.target.value)}
        />
        <br></br>
        <TextField
          label="Data de Nascimento"
          type="date"
          // fullWidth
          InputLabelProps={{ shrink: true }}
          value={form.birthDate}
          onChange={(e) => handleChange("birthDate", e.target.value)}
        />
        <br></br>
      </Box>
      <Button variant="contained" color="primary" onClick={handleSubmit}>
        Salvar
      </Button>
    </Box>
  );
}
