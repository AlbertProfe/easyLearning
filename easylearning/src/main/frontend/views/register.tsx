// frontend/views/register.tsx
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Dialog, TextField, PasswordField, Button, Upload } from '@vaadin/react-components';
import { useState } from 'react';
import { register } from 'Frontend/generated/UserEndpoint.ts';

export const config: ViewConfig = {
  menu: { exclude: true },
  flowLayout: false,
};

export default function RegisterView() {
  const error = useSignal('');
  const [username, setUsername] = useState('');
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [passwordRepeat, setPasswordRepeat] = useState('');
  const [avatarFile, setAvatarFile] = useState<File | null>(null);
  const [opened, setOpened] = useState(true);

  const dialogStyles = {
    maxWidth: '400px',
    margin: '0 auto',
  };

  const handleSubmit = async () => {
    if (password !== passwordRepeat) {
      error.value = 'Passwords do not match';
      return;
    }

    try {
      const result = await register(username, name, password, avatarFile);
      if (result.success) {
        document.location = '/login';
      } else {
        error.value = result.error || 'Registration failed';
      }
    } catch (e) {
      error.value = 'An error occurred during registration';
    }
  };

  const handleCancel = () => {
    document.location = '/home';
  };

  return (
    <Dialog
      opened={opened}
      onOpenedChanged={(e) => setOpened(e.detail.value)}
      header={
              <div
                style={{
                  backgroundColor: 'var(--lumo-primary-color)', // Matches theme="primary"
                  padding: 'var(--lumo-space-m)',
                  width: '100%',
                  marginLeft: 'calc(-1 * var(--lumo-space-m))',
                  marginRight: 'calc(-1 * var(--lumo-space-m))',
                  marginTop: 'calc(-1 * var(--lumo-space-m))', // Removes top gap
                }}
              >
                <h1 style={{ fontSize: '2rem', color: 'white', margin: 0 }}>Register</h1>
                <p style={{ fontSize: '0.875rem', color: 'white', margin: 0 }}>Create a new account</p>
              </div>
            }
      style={dialogStyles}
      footer={
        <div className="flex gap-m">
          <Button onClick={handleCancel}>Cancel</Button>
          <Button theme="primary" onClick={handleSubmit}>
            Register
          </Button>
        </div>
      }
    >
      <div className="p-m">
        {error.value && (
          <div className="text-error text-s mb-m">{error.value}</div>
        )}

        <TextField
          label="Username"
          value={username}
          onValueChanged={(e) => setUsername(e.detail.value)}
          className="w-full mb-m"
        />

         <TextField
          label="name"
          value={name}
          onValueChanged={(e) => setName(e.detail.value)}
          className="w-full mb-m"
        />

        <PasswordField
          label="Password"
          value={password}
          onValueChanged={(e) => setPassword(e.detail.value)}
          className="w-full mb-m"
        />

        <PasswordField
          label="Repeat Password"
          value={passwordRepeat}
          onValueChanged={(e) => setPasswordRepeat(e.detail.value)}
          className="w-full mb-m"
        />

        <Upload
          accept="image/*"
          maxFiles={1}
          onUploadBefore={(e) => {
            setAvatarFile(e.detail.files[0]);
          }}
          className="w-full mb-m"
          capture="user"
        >
          <span slot="drop-label">Upload avatar (optional)</span>
        </Upload>
      </div>
    </Dialog>
  );
}