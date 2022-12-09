import * as React from "react";
import { Admin, Resource } from "react-admin";
import simpleRestProvider from 'ra-data-json-server';
import authProvider from "./auth/authProvider";
import courses from './courses';

const dataProvider = simpleRestProvider('http://localhost:8080/admin')

const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider}>
        <Resource name="courses" {...courses} />
    </Admin>
);

export default App;
