"use client";
import { useContext, useState } from "react";
import {
  Box,
  Grid,
  Typography,
  Button,
  ToggleButton,
  ToggleButtonGroup,
  FormControlLabel,
  Switch,
} from "@mui/material";
import { ChromePicker } from "react-color";
import { AppContext } from "@components/context/AppContext";
import { ApiService } from "@services/ApiService";

export default function Settings({handleSubmit}: {handleSubmit:(field: string, value: string) => void}) {
  const [advancedMode, setAdvancedMode] = useState(false);
  const [bgColorTemp, setBgColorTemp] = useState("#ffffff");
  const [btnColorTemp, setBtnColorTemp] = useState("#1976d2");
  const context = useContext(AppContext);
  if (!context) return null;

  const { setBgColor, setBtnColor } = context;

  const presetBgColors = [
    "#ffffff",
    "#f0f0f0",
    "#121212",
    "#eeeeee",
    "#000000",
  ];
  const presetBtnColors = [
    "#1976d2",
    "#dc004e",
    "#388e3c",
    "#f57c00",
    "#9c27b0",
  ];

  const handleSelectBgColor = (color: string) => {
    setBgColorTemp(color);
    setBgColor(color);
  };

  const handleSelectBtnColor = (color: string) => {
    setBtnColorTemp(color);
    setBtnColor(color);
  };

  const handleChangeBgColor = (color: any) => {
    setBgColorTemp(color.hex);
    setBgColor(color.hex);
  };

  const handleChangeBtnColor = (color: any) => {
    setBtnColorTemp(color.hex);
    setBtnColor(color.hex);
  };

  return (
    <Box p={4}>
      <Typography variant="h5" gutterBottom>
        Personalização de Cores
      </Typography>

      <FormControlLabel
        control={
          <Switch
            checked={advancedMode}
            onChange={(e) => setAdvancedMode(e.target.checked)}
          />
        }
        label="Modo Avançado"
        sx={{ mb: 4 }}
      />

      <Grid container spacing={4}>
        <Grid>
          <Typography variant="h6">Cor de Fundo</Typography>
          {!advancedMode ? (
            <ToggleButtonGroup exclusive value={bgColorTemp}>
              {presetBgColors.map((color) => (
                <ToggleButton
                  key={color}
                  value={color}
                  disableRipple
                  onClick={() => handleSelectBgColor(color)}
                  selected={bgColorTemp === color}
                  sx={{
                    backgroundColor: color,
                    width: 40,
                    height: 40,
                    mx: 0.5,
                    border:
                      bgColorTemp === color
                        ? "2px solid #333"
                        : "1px solid #ccc",
                    "&.Mui-selected": {
                      backgroundColor: color + " !important",
                    },
                    "&.Mui-selected:hover": {
                      backgroundColor: color + " !important",
                    },
                    "&:hover": {
                      border: "2px solid #666",
                    },
                  }}
                />
              ))}
            </ToggleButtonGroup>
          ) : (
            <ChromePicker
              color={bgColorTemp}
              onChange={handleChangeBgColor}
              disableAlpha
            />
          )}
        </Grid>

        <Grid>
          <Typography variant="h6">Cor dos Botões</Typography>
          {!advancedMode ? (
            <ToggleButtonGroup exclusive value={btnColorTemp}>
              {presetBtnColors.map((color) => (
                <ToggleButton
                  key={color}
                  value={color}
                  onClick={() => handleSelectBtnColor(color)}
                  selected={btnColorTemp === color}
                  sx={{
                    backgroundColor: color,
                    width: 40,
                    height: 40,
                    mx: 0.5,
                    border:
                      bgColorTemp === color
                        ? "2px solid #333"
                        : "1px solid #ccc",
                    "&.Mui-selected": {
                      backgroundColor: color + " !important",
                    },
                    "&.Mui-selected:hover": {
                      backgroundColor: color + " !important",
                    },
                    "&:hover": {
                      border: "2px solid #666",
                    },
                  }}
                />
              ))}
            </ToggleButtonGroup>
          ) : (
            <ChromePicker
              color={btnColorTemp}
              onChange={handleChangeBtnColor}
              disableAlpha
            />
          )}
        </Grid>
      </Grid>

      <Box mt={4}>
        <Button
          variant="contained"
          color="primary"
          fullWidth
          onClick={() => handleSubmit(bgColorTemp, btnColorTemp)}
        >
          Salvar
        </Button>
      </Box>
    </Box>
  );
}
