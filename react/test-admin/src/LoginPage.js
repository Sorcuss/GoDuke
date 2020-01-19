import React from 'react';
import { Login, LoginForm } from 'react-admin';
import { withStyles } from '@material-ui/core/styles';

const styles = ({
    main: {
        background: 'linear-gradient(45deg, #5c6bc0 20%, #1a237e 90%)',
        border: 0,
        borderRadius: 3,
        boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
        color: 'white',
        height: 48,
        padding: '0 30px',
    },
    avatar: {
        background: 'url(https://goduke-react-app.s3.amazonaws.com/logo512.png)',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'contain',
        height: 80,
        "background-position": "center"
    },
    icon: { display: 'none' },
});

const CustomLoginForm = withStyles({
    button: { background: '#F15922' },
})(LoginForm);

const CustomLoginPage = props => (
    <Login
        backgroundImage=""
        loginForm={<CustomLoginForm />}
        {...props}
    />
);

export default withStyles(styles)(CustomLoginPage);