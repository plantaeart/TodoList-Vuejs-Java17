describe('Create ToDo List', () => {
  it('should create a new ToDo list', () => {
    cy.visit('/')

    // Click on the "Create todo list" button
    cy.get('[data-cy=showToDoListFormAddButton]').click()

    //* ADD TODO LIST
    // Select color of the todo list
    cy.get('[data-cy="addToDoListColorSelect"] > .p-select-dropdown').click()
    cy.get('#pv_id_1_3').click()

    // Select icon of the todo list
    cy.get('[data-cy="addToDoListIconSelect"] > .p-select-dropdown').click()
    cy.get('#pv_id_3_35').click()

    // Fill todo list name input
    cy.get('[data-cy=addToDoListNameInput]').type('My new todo list')

    // Fill todo list description input
    cy.get('[data-cy=addToDoListDescriptionInput]').type('My new todo list description')

    //* ADD TASK 1
    // Click on add Task button
    cy.get('[data-cy=addToDoListTaskAddButton]').click()
    // Fill task name input
    cy.get('[data-cy=addToDoListTaskNameInput-1]').type('Task1')
    // Select task icon
    cy.get('[data-cy=addToDoListTaskIconSelect-1] > .p-select-dropdown').click()
    cy.get('#pv_id_22_37').click()

    //* ADD TASK 1 SUBTASKS 1
    cy.get('[data-cy=addToDoListSubTaskAddButton-1]').click()
    // Fill subtask name input
    cy.get('[data-cy=addToDoListSubTaskNameInput-1-1]').type('Task1 Subtask1')
    // Select subtask icon
    cy.get('[data-cy=addToDoListSubTaskIconSelect-1-1] > .p-select-dropdown').click()
    cy.get('#pv_id_34_42').click()

    //* ADD TASK 1 SUBTASKS 2
    cy.get('[data-cy=addToDoListSubTaskAddButton-1]').click()
    // Fill subtask name input
    cy.get('[data-cy=addToDoListSubTaskNameInput-1-2]').type('Task1 Subtask2')
    // Select subtask icon
    cy.get('[data-cy=addToDoListSubTaskIconSelect-1-2] > .p-select-dropdown').click()
    cy.get('#pv_id_45_36').click()

    //* ADD TASK 1 SUBTASKS 3
    cy.get('[data-cy=addToDoListSubTaskAddButton-1]').click()
    // Fill subtask name input
    cy.get('[data-cy=addToDoListSubTaskNameInput-1-3]').type('Task1 Subtask3')
    // Select subtask icon
    cy.get('[data-cy=addToDoListSubTaskIconSelect-1-3] > .p-select-dropdown').click()
    cy.get('#pv_id_56_9').click()

    //* SWITCH SUBTASK 1 WITH SUBTASK 2
    cy.get('[data-cy=addToDoListSubTaskSwitchDownButton-1-1]').click()
    cy.get('[data-cy=addToDoListSubTaskNameInput-1-1]').should('have.value', 'Task1 Subtask1')

    //* ADD TASK 2
    // Click on add Task button
    cy.get('[data-cy=addToDoListTaskAddButton]').click()
    // Fill task name input
    cy.get('[data-cy=addToDoListTaskNameInput-2]').type('Task2')
    // Select task icon
    cy.get('[data-cy=addToDoListTaskIconSelect-2] > .p-select-dropdown').click()
    cy.get('.p-iconfield > .p-inputtext').type('cart')
    cy.get('#pv_id_67_0').click()

    //* ADD TASK 3
    // Click on add Task button
    cy.get('[data-cy=addToDoListTaskAddButton]').click()
    // Fill task name input
    cy.get('[data-cy=addToDoListTaskNameInput-3]').type('Task3')
    // Select task icon
    cy.get('[data-cy=addToDoListTaskIconSelect-3] > .p-select-dropdown').click()
    cy.get('#pv_id_79_27').click()

    //* ADD TASK 3 SUBTASKS 1
    cy.get('[data-cy=addToDoListSubTaskAddButton-3]').click()
    // Fill subtask name input
    cy.get('[data-cy=addToDoListSubTaskNameInput-3-1]').type('Task3 Subtask1')
    // Select subtask icon
    cy.get('[data-cy=addToDoListSubTaskIconSelect-3-1] > .p-select-dropdown').click()
    cy.get('#pv_id_91_0').click()

    //* SWITCH TASK 1 DOWN TO THE LIST
    cy.get('[data-cy=addToDoListTaskSwitchDownButton-1]').click()
    cy.get('[data-cy=addToDoListTaskSwitchDownButton-2]').click()
    cy.get('[data-cy=addToDoListTaskNameInput-1]').should('have.value', 'Task1')

    //* CREATE THE TODO LIST
    cy.get('[data-cy="addToDoListButton"]').click()

    // Check if the new todo list is displayed in the list
    cy.get('[data-cy=toDoList-1]').should('exist')

    // Delete todo list
    cy.get('[data-cy="deleteToDoList-1"]').click()

    // Check that the todo list is deleted
    cy.get('[data-cy=toDoList-1]').should('not.exist')
  })
})
