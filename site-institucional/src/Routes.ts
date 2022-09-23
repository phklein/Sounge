
import React from 'react'
import { BrowserRouter, Route } from 'react-router-dom'

import { isAuthenticated } from './Auth'

// const PrivateRoute = ({ component: Component, ...rest }) => (
//    <Route
//         {...rest}
//         render={props =>
//             isAuthenticated() ? (
//                 <Component {...props} />
//             ) : (
//                 <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
//             )
//         }
//     />
// ) 

