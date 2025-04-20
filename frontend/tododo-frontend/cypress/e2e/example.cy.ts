// https://on.cypress.io/api

describe('My First Test', () => {
  it('visits the app root url', () => {
    console.log('test')
    cy.visit('/')
  })
})
