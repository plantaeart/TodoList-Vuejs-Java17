<script setup lang="ts">
import { ref } from 'vue'
import InputText from 'primevue/inputtext'
import Button from 'primevue/button'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import { Result } from '@/types/result'
import { Task } from '@/features/task/Task'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const toDoListName = ref('')
const tasks = ref<Task[]>([])

const addToDoListFromFormInfos = async () => {
  let resp: ToDoListResponse = new ToDoListResponse()
  try {
    req.toDoLists = [
      { name: toDoListName.value, tasks: tasks.value.filter((item) => item.taskContent !== '') },
    ] // Prepare the request
    console.log(`Start adding todo list with name : ` + req.toDoLists)
    // Await the response from the store
    resp = await store.addToDoList(req)
    // Check if the response is valid and contains the expected data
    if (resp && resp.currentResult === Result.OK) {
      tasks.value.splice(0, tasks.value.length) // Clear the tasks array after successful addition
    } else console.error(`Failed to add ToDoList. Response : ${resp.message}`)
  } catch (error) {
    console.error(`(FRONT) Error while adding ToDoList : ${error}`)
    console.error(`(BACK) Error while adding ToDoList : ${resp.message}`)
  }
}

// Function to add a new task in form
const addingNewTask = async () => {
  if (!toDoListName.value) return

  tasks.value.push(new Task(tasks.value.length + 1, ''))
}

// Function to delete a task in form
const deleteNewTask = async (taskId: number) => {
  if (!toDoListName.value) return

  const index = tasks.value.findIndex((task) => task.id === taskId)
  if (index !== -1) {
    tasks.value.splice(index, 1)
  }
}

defineExpose({
  addToDoListFromFormInfos,
})
</script>

<template>
  <div class="flex flex-col items-center m-4 w-lg">
    <div id="formElements" class="flex flex-col gap-2 w-1/2">
      <label for="toDoListName">Todo list name</label>
      <InputText class="w-full" v-model="toDoListName" aria-describedby="todo list name" />
      <div v-for="task in tasks" :key="task.id" class="flex flex-row justify-end items-center">
        <InputText class="w-3/4" v-model="task.taskContent" aria-describedby="task name" />
        <Button
          class="ml-1"
          size="small"
          icon="pi pi-times"
          severity="danger"
          aria-label="Cancel"
          @click="deleteNewTask(task.id)"
        />
      </div>
      <div class="flex flex-row items-center justify-center">
        <Button
          label="Add task"
          icon="pi pi-plus"
          class="w-1/2"
          @click="addingNewTask"
          :disabled="!toDoListName"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
