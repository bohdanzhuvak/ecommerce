import type { ReactNode } from 'react';
import {
  AppBar,
  CheckForApplicationUpdate,
  Layout as RALayout,
  Logout,
  useGetIdentity,
  UserMenu,
} from 'react-admin';
import {
  Box,
  ListItemIcon,
  ListItemText,
  MenuItem,
  Typography,
} from '@mui/material';
import { Person as PersonIcon } from '@mui/icons-material';

const CustomUserMenu = () => {
  const { data: identity } = useGetIdentity();

  return (
    <UserMenu>
      <MenuItem>
        <ListItemIcon>
          <PersonIcon fontSize="small" />
        </ListItemIcon>
        <ListItemText>
          <Typography variant="body2">
            {identity?.username || 'User'}
          </Typography>
          <Typography variant="caption" color="text.secondary">
            {identity?.email || ''}
          </Typography>
        </ListItemText>
      </MenuItem>
      <Logout />
    </UserMenu>
  );
};

const CustomAppBar = () => (
  <AppBar userMenu={<CustomUserMenu />}>
    <Box sx={{ flex: 1, display: 'flex', alignItems: 'center' }}>
      <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
        Admin Panel | Ecommerce
      </Typography>
    </Box>
  </AppBar>
);

export const Layout = ({ children }: { children: ReactNode }) => (
  <RALayout appBar={CustomAppBar}>
    {children}
    <CheckForApplicationUpdate />
  </RALayout>
);
