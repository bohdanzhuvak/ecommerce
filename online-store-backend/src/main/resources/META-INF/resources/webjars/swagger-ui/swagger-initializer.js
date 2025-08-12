window.onload = function () {

    function setSwaggerAuth(token, system) {
        if (token) {
            const authData = {
                bearerAuth: {
                    name: "bearerAuth",
                    schema: {
                        type: "http",
                        name: "Authorization",
                        in: "header",
                        scheme: "bearer",
                        bearerFormat: "JWT"
                    },
                    value: token
                }
            };

            localStorage.setItem('authorized', JSON.stringify(authData));
            system.authActions.authorize({
                bearerAuth: {
                    name: "Authorization",
                    schema: {
                        type: "http",
                        scheme: "bearer",
                        bearerFormat: "JWT"
                    },
                    value: token
                }
            });
        } else {
            localStorage.removeItem('authorized');
            try { system.authActions.logout(['bearerAuth']); } catch {}
        }
    }

    async function login(system) {
        try {
            const email = window.prompt('Email');
            if (!email) return;
            const password = window.prompt('Password');
            if (password === null) return;

            const res = await fetch('/api/v1/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            const body = await res.json()

            if (!res.ok) {
                alert(body.message || 'Login failed');
                return;
            }

            const token = body.token || null;
            if (!token) {
                alert('No token in response');
                return;
            }

            setSwaggerAuth(token, system);
            alert('Logged in');
        } catch (e) {
            console.warn('JWT login error', e);
            alert('Login error, see console');
        }
    }

    function logout(system) {
        setSwaggerAuth(null, system);
        alert('Logged out');
    }

    const JwtAuthPlugin = function() {
        return {
            wrapComponents: {
                AuthorizeBtnContainer: (Original, system) => (props) => {
                    const React = system.React;
                    return React.createElement(
                        'div',
                        { style: { display: 'flex', alignItems: 'center', gap: '8px' } },
                        [
                            React.createElement(Original, props),
                            React.createElement('div', { className: 'auth-wrapper'}, [
                                React.createElement('button', {
                                    className: 'btn',
                                    onClick: () => login(system),
                                    style: { marginLeft: '10px' }
                                }, 'Login')
                            ]),
                            React.createElement('div', { className: 'auth-wrapper'}, [
                                React.createElement('button', {
                                    className: 'btn',
                                    onClick: () => logout(system),
                                    style: { marginLeft: '10px' }
                                }, 'Logout')
                            ]),
                        ]
                    );
                }
            }
        };
    };

    window.ui = SwaggerUIBundle({
        url: '/v3/api-docs',
        dom_id: '#swagger-ui',
        deepLinking: true,
        displayRequestDuration: true,
        persistAuthorization: true,
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        layout: 'StandaloneLayout',
        plugins: [ JwtAuthPlugin ],
    });
};
