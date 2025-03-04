import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button, Dialog, TextField } from '@vaadin/react-components';
import { register } from 'Frontend/generated/RegisterEndpoint.ts';
import { useNavigate } from 'react-router-dom';

export const config: ViewConfig = {
  menu: { exclude: true },
  flowLayout: false,
};

export default function RegisterView() {
  const navigate = useNavigate();
  const username = useSignal('');
  const password = useSignal('');
  const name = useSignal('');
  const error = useSignal('');

  const handleRegister = async () => {
    try {
      const response = await register(username.value, password.value, name.value);
      if (response === 'User registered successfully!') {
        navigate('/login');
      } else {
        error.value = response;
      }
    } catch (e) {
      error.value = 'Registration failed. Please try again.';
    }
  };

  return (
    <Dialog
      opened
      headerTitle="Register"
      onClosed={() => navigate(-1)}
    >
      <div className="flex flex-col gap-m">
        <p className="text-center">Create a new account</p>
        <TextField
          label="Username"
          value={username.value}
          onValueChanged={(e) => (username.value = e.detail.value)}
          className="w-full"
        />
        <TextField
          label="Password"
          type="password"
          value={password.value}
          onValueChanged={(e) => (password.value = e.detail.value)}
          className="w-full"
        />
        <TextField
          label="Name"
          value={name.value}
          onValueChanged={(e) => (name.value = e.detail.value)}
          className="w-full"
        />
        <Button theme="primary" onClick={handleRegister} className="w-full">
          Register
        </Button>
        {error.value && <p className="text-red-500 text-center">{error.value}</p>}

      <p slot="footer" className="text-center">
        Already have an account?{' '}
        <a href="/login" className="text-blue-500 hover:underline">
          Login here
        </a>
      </p>

       </div>
    </Dialog>
  );
}