import * as React from "react";
import { Admin } from "react-admin";
import simpleRestProvider from 'ra-data-json-server';
import authProvider from "./auth/authProvider";


const dataProvider = simpleRestProvider('http://localhost:8080/admin')

const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider}>
    </Admin>
    );

export default App;
