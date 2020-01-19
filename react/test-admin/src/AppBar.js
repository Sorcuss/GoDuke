import React from 'react';
import { AppBar } from 'react-admin';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';




const MyAppBar = props => {
    const useStyles = makeStyles((theme) => ({
        title: {
            flex: 1,
            textOverflow: 'ellipsis',
            whiteSpace: 'nowrap',
            overflow: 'hidden',
        },
        spacer: {
            flex: 1,
        },
        bar:{
            background: 'linear-gradient(45deg, #5c6bc0 20%, #1a237e 90%)'
        },
        root: {
            display: 'flex',
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        small: {
            width: theme.spacing(3),
            height: theme.spacing(3),
        },
        large: {
            width: theme.spacing(6),
            height: theme.spacing(6),
            margin: "5px"
        },
    }));
    const classes = useStyles();
    return (
        <AppBar
            className={classes.bar}
            {...props}>
            <Typography
                variant="h6"
                color="inherit"
                className={classes.title}
                id="react-admin-title"
            />
            <Avatar alt="Logo" src="https://goduke-react-app.s3.amazonaws.com/logo192.png" className={classes.large}/>
            <span className={classes.spacer} />
        </AppBar>
    );
};

export default MyAppBar;