"use client";
import {
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  Icon,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";
import { Grid } from "@mui/system";
import Box from "@mui/material/Box";
import { useEffect, useState } from "react";
import { useUserStore } from "@stores/userStores";
import Utils from "@utils/Utils";
import CloseIcon from "@mui/icons-material/Close";
import AddIcon from "@mui/icons-material/Add";
import EditProfilePage from "./edit/page";
import { useRouter } from "@next/navigation";
import { User } from "@interfaces/User";
import { ApiService } from "@services/ApiService";
import AddLocationPage from "./(location)/location/edit/page";
import DisplaySettingsIcon from "@mui/icons-material/DisplaySettings";
import Settings from "../settings/page";

export function Profile() {
  const { user, setUser } = useUserStore();
  const [open, setOpen] = useState<boolean>(false);
  const [openLocation, setOpenLocation] = useState<boolean>(false);
  const [openSettings, setOpenSettings] = useState<boolean>(false);
  const [form, setForm] = useState<User>(user);

  const handleSubmit = async () => {
    setUser(form);
    const response = await ApiService.updateUser({ user: form });
    if (response && response.status === 201) {
      localStorage.setItem("user", JSON.stringify(response.data));
      setOpen(false);
    }
  };

  const handleChange = (field: keyof User, value: string) => {
    setForm({ ...form, [field]: value });
  };

  const handleChangeLocation = (field: keyof Location, value: string) => {
    const updatedLocations = [...form.locations];
    updatedLocations[0] = { ...updatedLocations[0], [field]: value };

    setForm({ ...form, locations: updatedLocations });
  };

  const handleSaveSettings = async (bgColorTemp: string, btnColorTemp: string) => {
    const prefs = { bgColor: bgColorTemp, btnColor: btnColorTemp };
    const response = await ApiService.savePreferences({ prefs: prefs });

    if (response && response.status === 201) {
      localStorage.setItem("prefs", JSON.stringify(response.data));
      setOpenSettings(false);
    }
  };

  useEffect(() => {
    const storedUser = localStorage.getItem("user");

    if (storedUser) {
      const parsedUser = JSON.parse(storedUser);
      useUserStore.setState({ user: parsedUser });
    }
  }, []);

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "50vh",
      }}
    >
      <Box sx={{ p: 4, border: 1, borderRadius: 2, minWidth: 400 }}>
        <Grid container justifyContent="space-between">
          <Typography variant="h4" mb={4} textAlign="center">
            Perfil
          </Typography>
          <IconButton onClick={() => setOpenSettings(true)}>
            <Tooltip title="Configurações">
              <DisplaySettingsIcon fontSize="inherit"></DisplaySettingsIcon>
            </Tooltip>
          </IconButton>
        </Grid>
        <Dialog
          open={openSettings}
          onClose={() => setOpenSettings(false)}
          fullWidth
        >
          <Box position="relative">
            <IconButton
              onClick={() => setOpenSettings(false)}
              sx={{ position: "absolute", right: 8, top: 8 }}
            >
              <CloseIcon fontSize="inherit" />
            </IconButton>

            <DialogTitle sx={{ textAlign: "center" }}>
              Configurações
            </DialogTitle>
          </Box>

          <Settings handleSubmit={handleSaveSettings} />
        </Dialog>
        <Grid container spacing={4} justifyContent="space-between">
          <Grid>
            <Typography>
              <strong>Nome:</strong>{" "}
              {user.firstName && user.lastName
                ? user.firstName + " " + user.lastName
                : "-"}
            </Typography>
            <Typography>
              <strong>Email:</strong> {user.email ? user.email : "-"}
            </Typography>
            <Typography>
              <strong>Telefone:</strong>{" "}
              {user.cellPhoneNumber
                ? Utils.cellPhoneMask(user.cellPhoneNumber)
                : "-"}
            </Typography>
            <Typography>
              <strong>CPF:</strong> {user.cpf ? Utils.maskToCpf(user.cpf) : "-"}
            </Typography>
            <Typography>
              <strong>Data de Nascimento:</strong>{" "}
              {user.birthDate ? Utils.dateToBrazilianDate(user.birthDate) : "-"}
            </Typography>
          </Grid>
          {user.locations && user.locations[0].cep !== null ? (
            <Grid>
              <Typography>
                <strong>Endereço:</strong>{" "}
                {user.locations && user.locations[0].street
                  ? user.locations[0].street
                  : "-"}
              </Typography>
              <Typography>
                <strong>CEP:</strong>{" "}
                {user.locations && user.locations[0].cep
                  ? user.locations[0].cep
                  : "-"}
              </Typography>
              <Typography>
                <strong>Cidade:</strong>{" "}
                {user.locations && user.locations[0].city
                  ? user.locations[0].city
                  : "-"}
              </Typography>
              <Typography>
                <strong>Estado:</strong>{" "}
                {user.locations && user.locations[0].state
                  ? user.locations[0].state
                  : "-"}
              </Typography>
              <Typography>
                <strong>País:</strong>{" "}
                {user.locations && user.locations[0].country
                  ? user.locations[0].country
                  : "-"}
              </Typography>
              <Typography>
                <strong>Complemento:</strong>{" "}
                {user.locations && user.locations[0].complement
                  ? user.locations[0].complement
                  : "-"}
              </Typography>
              <Typography>
                <strong>Número:</strong>{" "}
                {user.locations && user.locations[0].number
                  ? user.locations[0].number
                  : "-"}
              </Typography>
              <Typography>
                <strong>Bairro:</strong>{" "}
                {user.locations && user.locations[0].neighborhood
                  ? user.locations[0].neighborhood
                  : "-"}
              </Typography>
            </Grid>
          ) : (
            <Grid>
              <Typography>
                <strong>Endereços de entrega:</strong>
              </Typography>
              <Button
                variant="contained"
                sx={{ mt: 2 }}
                size="small"
                onClick={() => setOpenLocation(true)}
              >
                <AddIcon fontSize="inherit" />
                Adicionar novo endereço
              </Button>
              <Dialog
                open={openLocation}
                onClose={() => setOpenLocation(false)}
              >
                <Box position="relative">
                  <IconButton
                    onClick={() => setOpenLocation(false)}
                    sx={{ position: "absolute", right: 8, top: 8 }}
                  >
                    <CloseIcon fontSize="inherit" />
                  </IconButton>

                  <DialogTitle sx={{ textAlign: "center" }}>
                    Adicionar Endereço
                  </DialogTitle>
                </Box>
                <AddLocationPage
                  location={form.locations && form.locations[0]}
                  handleChange={handleChangeLocation}
                  handleSubmit={handleSubmit}
                />
              </Dialog>
            </Grid>
          )}
        </Grid>
        <Box textAlign="center" mt={4}>
          <Button onClick={() => setOpen(true)} variant="contained">
            Editar Perfil
          </Button>
          <Dialog open={open} maxWidth="sm">
            <Box position="relative">
              <IconButton
                onClick={() => setOpen(false)}
                sx={{ position: "absolute", right: 8, top: 8 }}
              >
                <CloseIcon fontSize="inherit" />
              </IconButton>

              <DialogTitle sx={{ textAlign: "center" }}>
                Editar Perfil
              </DialogTitle>
            </Box>

            <EditProfilePage
              form={form}
              handleChange={handleChange}
              handleSubmit={handleSubmit}
            />
          </Dialog>
        </Box>
      </Box>
    </Box>
  );
}

export default Profile;
