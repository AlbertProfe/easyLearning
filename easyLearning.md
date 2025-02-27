# easyLearning

## Vaadin-Hilla

Let’s break this down to clarify the communication between the frontend and backend in a Hilla application.

Understanding Hilla’s Architecture.

Hilla (built on Vaadin) uses a combination of technologies to facilitate communication between the backend (Java/Spring Boot) and the frontend (React/TypeScript):

- **WebSocket Layer**: Hilla leverages the Atmosphere Framework to establish a WebSocket connection for real-time, bidirectional communication between the frontend and backend. This is used for features like server push (e.g., updates from the server to the client without polling) and endpoint calls (e.g., invoking Java methods from the frontend).

- **Frontend Components:** Hilla provides React components (@hilla/react-components/*) that integrate seamlessly with this WebSocket layer, allowing UI updates to reflect backend changes efficiently.

- **Generated Bindings**: Hilla generates TypeScript definitions (e.g., Frontend/generated/endpoints.ts) from your Java @Endpoint classes, enabling type-safe calls to backend methods via the WebSocket connection.

## Atmosphere

> `Hilla` leverages the **Atmosphere framework** to enable real-time, bidirectional communication between the client and server using WebSockets. 

`Atmosphere` abstracts the complexity of handling WebSocket connections, fallbacks, and browser compatibility, providing a robust foundation for real-time features. 

`Hilla` integrates `Atmosphere` to facilitate seamless communication in its `@Endpoint` classes, allowing developers to focus on business logic rather than low-level WebSocket management. When a client interacts with a Hilla endpoint, Atmosphere manages the connection, ensuring efficient data exchange and automatic reconnection in case of disruptions. 

## Backend Boot

The project structure is organized into a standard Spring Boot application layout.:

- The `EasyLearningApplication.java` is the entry point, initializing the Spring Boot application. 

- The `controller` package contains `LearningQuestionController.java`, which handles HTTP requests and responses. 

- The `model` package includes `LearningQuestion.java`, defining the data structure for quiz questions. 

- The `repository` package houses `LearningQuestionRepository.java`, managing database interactions. 

- The `service` package contains `LearningQuestionService.java` for business logic and `QuizEndpoint.java` for additional API endpoints. 

This modular structure ensures separation of concerns, making the application scalable, maintainable, and easy to test. It follows best practices for Spring Boot development.

```bash
$ tree
.
├── EasyLearningApplication.java
├── controller
│   └── LearningQuestionController.java
├── model
│   └── LearningQuestion.java
├── repository
│   └── LearningQuestionRepository.java
└── service
    ├── LearningQuestionService.java
    └── QuizEndpoint.java
```

Application.properties

```properties
vaadin.launch-browser=true
spring.application.name=easyTest

server.port=8080

# H2 DATABASE SERVER
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# H2 IN MEMORY
#spring.datasource.url=jdbc:h2:mem:testdb
#spring.datasource.username=sa
#spring.datasource.password=


# H2 LOCAL DB SERVER
spring.datasource.url=jdbc:h2:/home/albert/MyProjects/SpringProjects/easyLearning/db/easyLearningDB.db
spring.datasource.username=albert
spring.datasource.password=1234

# DDL OPTIONS: create-drop, create, update, none, validate
spring.jpa.hibernate.ddl-auto=update
```

### Service

The `QuizEndpoint` class is a <mark>Hilla endpoint</mark>, marked with `@Endpoint`, enabling <mark>WebSocket-based communication between the client and server</mark>. 

> This allows real-time, bidirectional interaction, enhancing responsiveness and reducing latency compared to traditional REST APIs. 

The `@AnonymousAllowed` annotation permits unauthenticated access, making the endpoint publicly available. The class injects `LearningQuestionService` to fetch quiz questions. 

```java
package com.example.easyLearning.service;

import com.example.easyLearning.model.LearningQuestion;
import com.example.easyLearning.service.LearningQuestionService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import com.vaadin.hilla.Nonnull;
import java.util.List;

@Endpoint
@AnonymousAllowed
public class QuizEndpoint {

    private final LearningQuestionService service;

    public QuizEndpoint(LearningQuestionService service) {
        this.service = service;
    }

    public @Nonnull List<LearningQuestion> getQuestions() {
        return service.getQuestions();
    }
}
```

## Frontend React

```bash
$ tree -L 2
.
├── frontend
│   ├── generated
│   ├── index.html
│   ├── themes
│   └── views
│       ├── @index.tsx
│       └── QuizView.tsx
├── java
│   └── ...
└── resources
    ├── application.properties
    ├── static
    └── templates

```

### Routes

Hilla manages routes and views in a structured and intuitive way, typically using React for the frontend. Here's how it works with `@index.tsx` and `QuizView.tsx`:

1. **`@index.tsx`**:
   
   - This file acts as the **entry point** for routing in <mark>Hilla</mark> applications.
   - It defines the main layout and routes for the application using <mark>React Router.</mark>
   - For example, it might map the root path (`/`) or other paths to specific views like `QuizView.tsx`.

2. **`QuizView.tsx`**:
   
   - This is a **React component** representing a specific view or page in the application.
   - It contains the UI and logic for displaying quiz-related content.
   - `Hilla` automatically integrates this view into the routing system when referenced in `@index.tsx`.

#### How Hilla Manages Routes:

- **React Router Integration**: Hilla uses React Router under the hood to handle client-side routing.
- **Dynamic Routing**: Routes are defined in `@index.tsx`, mapping URLs to corresponding views like `QuizView.tsx`.
- **Lazy Loading**: Views can be dynamically loaded to improve performance, ensuring only the required components are loaded when needed.
- **Seamless Navigation**: Hilla ensures smooth transitions between views, enhancing the user experience.

Example of `@index.tsx`:

```jsx
import QuizView from "./QuizView";
import { ThemeProvider, createTheme } from '@mui/material/styles';

const theme = createTheme(); // You can customize this later

export default function HomeView() {
  return (
    <>
     <ThemeProvider theme={theme}>
     <div style={{
                     width: "500px", // Set a fixed width (adjust as needed)
                     margin: "0 auto", // Centers horizontally
                     border: "1px solid #ccc",
                     padding: "40px"  }}>
          <QuizView />
        </div>
     </ThemeProvider>
    </>
  );
}


```

Key benefits:

- **Modularity**: Views like `QuizView.tsx` are self-contained and reusable.
- **Scalability**: Adding new routes and views is straightforward.
- **Performance**: Lazy loading ensures efficient resource usage.

Hilla's routing system simplifies navigation and ensures a clean, maintainable structure for modern web applications.



## API Rest

We will use `ResponseEntity` for all`endpoints :

### Code with `ResponseEntity`:

```java
package com.example.easyLearning.controller;

import com.example.easyLearning.model.LearningQuestion;
import com.example.easyLearning.repository.LearningQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LearningQuestionController {
    @Autowired
    LearningQuestionRepository learningQuestionRepository;

    // Get all questions
    @GetMapping("/api/v1/questions")
    public ResponseEntity<List<LearningQuestion>> getAll() {
        List<LearningQuestion> questions = learningQuestionRepository.findAll();
        return ResponseEntity.ok(questions); // Return 200 OK with the list of questions
    }

    // Get a question by ID
    @GetMapping("/api/v1/questions/{id}")
    public ResponseEntity<LearningQuestion> getById(@PathVariable Long id) {
        return learningQuestionRepository.findById(id)
                .map(question -> ResponseEntity.ok(question)) // Return 200 OK with the question
                .orElse(ResponseEntity.notFound().build()); // Return 404 Not Found if the question doesn't exist
    }
}
```

1. **`getAll` Method**:
   
   - The `getAll` method  returns `ResponseEntity<List<LearningQuestion>>`.
   - `ResponseEntity.ok(questions)` is used to return a `200 OK` HTTP status along with the list of questions.

2. **`getById` Method**:
   
   - The `getById` method is using `ResponseEntity` to handle both success (`200 OK`) and failure (`404 Not Found`) cases.

Benefits of Using `ResponseEntity`:

- **Control Over HTTP Status**: You can explicitly set the HTTP status code (e.g., `200 OK`, `404 Not Found`).
- **Custom Headers**: You can add custom headers to the response if needed.
- **Consistency**: Using `ResponseEntity` makes your API responses consistent and easier to manage.

### Example Usage:

- **Get All Questions**: `GET /api/v1/questions`
  
  - If successful, returns `200 OK` with the list of questions.
  
  - Example response body:
    
    ```json
    [
        {
            "id": 1,
            "question": "What is the capital of France?",
            "options": ["Paris", "London", "Berlin"],
            "correctAnswer": "Paris"
        },
        {
            "id": 2,
            "question": "What is 2 + 2?",
            "options": ["3", "4", "5"],
            "correctAnswer": "4"
        }
    ]
    ```

- **Get Question by ID**: `GET /api/v1/questions/1`
  
  - If the question exists, returns `200 OK` with the question.
  
  - Example response body:
    
    ```json
    {
       ""id": 1,
        "question": "What is the capital of France?",
        "options": ["Paris", "London", "Berlin"],
        "correctAnswer": "Paris"
    }
    ```
  
  - If the question does not exist, returns `404 Not Found`.

- Optional: Error Handling for `getAll`
  
  - If you want to handle cases where no questions are found (e.g., return `404 Not Found`), you can modify the `getAll` method as follows:

```java
If the question does not exist, returns 404 Not Found.

Optional: Error Handling for getAll
If you want to handle cases where no questions are found (e.g., return 404 Not Found), you can modify the getAll method as follows:

java
Copy
@GetMapping("/api/v1/questions")
public ResponseEntity<List<LearningQuestion>> getAll() {
    List<LearningQuestion> questions = learningQuestionRepository.findAll();
    if (questions.isEmpty()) {
        return ResponseEntity.notFound().build(); // Return 404 Not Found if no questions are found
    }
    return ResponseEntity.ok(questions); // Return 200 OK with the list of questions
}
This ensures that your API provides meaningful responses in all scenarios.
```
