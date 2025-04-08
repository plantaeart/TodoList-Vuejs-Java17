<script setup lang="ts">
import { onMounted, ref } from 'vue'
import InputText from 'primevue/inputtext'
import Textarea from 'primevue/textarea'
import FloatLabel from 'primevue/floatlabel'
import Button from 'primevue/button'
import Select from 'primevue/select'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import { Result } from '@/types/result'
import { Task } from '@/features/task/Task'
import { ToDoList } from '@/features/toDoList/ToDoList'
import { icons } from '@/constants/icons'
import { colors } from '@/constants/colors'
import { Icon } from '@/features/classes/Icon'
import { rearrangeArrayIds } from '@/utils/arrayUtils'
import { Color } from '@/features/classes/Color'
import { SubTask } from '@/features/subTask/SubTask'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const toDoListToAdd = ref(new ToDoList())
const tasks = ref<Task[]>([])
const defaultIcon = new Icon('Star', 'pi pi-star')
const defaultColor = new Color('Yellow 100', 'bg-yellow-100')

onMounted(() => {
  toDoListToAdd.value.icon = defaultIcon
  toDoListToAdd.value.color = defaultColor
})

const addToDoListFromFormInfos = async () => {
  let resp: ToDoListResponse = new ToDoListResponse()
  // Adding the icon to correct task
  try {
    req.toDoLists = [
      {
        name: toDoListToAdd.value.name,
        description: toDoListToAdd.value.description,
        icon: toDoListToAdd.value.icon,
        color: toDoListToAdd.value.color,
        tasks: tasks.value.filter((item) => item.taskContent !== ''),
      },
    ] // Prepare the request
    console.log(`Start adding todo list with name : ` + req.toDoLists[0].name)
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
  if (!toDoListToAdd.value.name) return
  const indexTask = tasks.value.length
  const idTask = indexTask + 1
  tasks.value.push(new Task({ id: idTask, taskContent: '', icon: defaultIcon }))
  tasks.value[indexTask].icon = defaultIcon
}

// Function to add a new task in form
const addingNewSubTask = async (idTask: number) => {
  const indexTask = idTask - 1
  const indexSubTask = tasks.value[indexTask].subTasks.length
  if (!tasks.value[indexTask].taskContent) return
  const idSubTask = indexSubTask + 1
  tasks.value[indexTask].subTasks.push(
    new SubTask({ id: idSubTask, taskContent: '', icon: defaultIcon }),
  )
}

// Function to delete a task in form
const deleteNewTask = async (idTask: number) => {
  if (!toDoListToAdd.value.name) return

  const index = tasks.value.findIndex((task) => task.id === idTask)
  if (index !== -1) {
    tasks.value.splice(index, 1)
  }

  tasks.value = rearrangeArrayIds(tasks.value)
}

const deleteNewSubTask = async (idTask: number, idSubTask: number) => {
  const indexTask = idTask - 1
  if (!tasks.value[indexTask].taskContent) return

  const indexSubTask = tasks.value[indexTask].subTasks.findIndex(
    (subTask) => subTask.id === idSubTask,
  )
  if (indexSubTask !== -1) {
    tasks.value[indexTask].subTasks.splice(indexSubTask, 1)
  }

  tasks.value[indexTask].subTasks = rearrangeArrayIds(tasks.value[indexTask].subTasks)
}

const clearDescription = async () => {
  toDoListToAdd.value.description = ''
}

defineExpose({
  addToDoListFromFormInfos,
})
</script>

<template>
  <div class="flex flex-col items-center m-4 w-lg">
    <div id="formElements" class="flex flex-col gap-2 w-4/5 justify-center">
      <div class="flex flex-row items-end justify-center">
        <!-- SELECT FOR COLORS -->
        <Select
          v-model="toDoListToAdd.color"
          :options="colors"
          filter
          optionLabel="name"
          class="w-1/4 mr-4 size-10"
          size="large"
        >
          <!-- Custom selected value template -->
          <template #value="slotProps">
            <div v-if="slotProps.value" class="flex items-center justify-center w-full">
              <span :class="['h-5', 'w-full', 'rounded-md', slotProps.value.color]" />
            </div>
            <span v-else>{{ slotProps.placeholder }}</span>
          </template>

          <!-- Custom dropdown options template -->
          <template #option="slotProps">
            <div class="flex items-center w-full">
              <span :class="['h-5', 'w-full', 'rounded-md', slotProps.option.color]" />
            </div>
          </template>
        </Select>
        <!-- SELECT FOR TODOLIST ICON -->
        <Select
          v-model="toDoListToAdd.icon"
          :options="icons"
          filter
          optionLabel="name"
          class="w-1/4 mr-4 size-10"
          size="large"
        >
          <!-- Custom selected value template -->
          <template #value="slotProps">
            <div v-if="slotProps.value" class="flex items-center">
              <span :class="['mr-2', 'text-lg', slotProps.value.icon]"></span>
            </div>
            <span v-else>{{ slotProps.placeholder }}</span>
          </template>

          <!-- Custom dropdown options template -->
          <template #option="slotProps">
            <div class="flex items-center w-1/3">
              <span :class="['mr-2', 'text-lg', slotProps.option.icon]"></span>
            </div>
          </template>
        </Select>
        <div class="flex flex-col">
          <label for="toDoListName">Todo list name</label>
          <InputText
            class="w-full"
            v-model="toDoListToAdd.name"
            aria-describedby="todo list name"
          />
        </div>
      </div>
      <div class="flex flex-col items-center">
        <FloatLabel variant="in">
          <Textarea
            id="TodoListDescription"
            v-model="toDoListToAdd.description"
            rows="3"
            cols="40"
            style="resize: none"
            maxlength="100"
          />
          <label for="over_label">Todo list description</label>
        </FloatLabel>
        <div class="w-full flex flex-row justify-start ml-19">
          <Button
            label="Clear"
            class="w-1/5"
            size="small"
            icon="pi pi-trash"
            severity="danger"
            aria-label="Clear"
            @click="clearDescription()"
          />
        </div>
      </div>

      <div v-for="(task, indexTask) in tasks" :key="task.id" class="">
        <div class="flex flex-row justify-end items-center">
          <!-- SELECT FOR TASK ICON -->
          <Select
            v-model="task.icon"
            :options="icons"
            filter
            optionLabel="name"
            class="w-1/5 mr-4 size-10"
            size="large"
          >
            <!-- Custom selected value template -->
            <template #value="slotProps">
              <div v-if="slotProps.value" class="flex items-center">
                <span :class="['mr-2', 'text-lg', slotProps.value.icon]"></span>
              </div>
              <span v-else>{{ slotProps.placeholder }}</span>
            </template>

            <!-- Custom dropdown options template -->
            <template #option="slotProps">
              <div class="flex items-center w-1/3">
                <span :class="['mr-2', 'text-lg', slotProps.option.icon]"></span>
              </div>
            </template>
          </Select>
          <InputText class="w-3/7" v-model="task.taskContent" aria-describedby="task name" />
          <Button
            class="ml-1"
            size="small"
            icon="pi pi-times"
            severity="danger"
            aria-label="Cancel"
            @click="deleteNewTask(task.id)"
          />
        </div>
        <div>
          <div
            v-for="subTask in tasks[indexTask].subTasks"
            :key="subTask.id"
            class="flex flex-row justify-end items-center ml-10 mt-2"
          >
            <!-- SELECT FOR TASK ICON -->
            <Select
              v-model="subTask.icon"
              :options="icons"
              filter
              optionLabel="name"
              class="w-1/5 mr-4 size-10"
              size="large"
            >
              <!-- Custom selected value template -->
              <template #value="slotProps">
                <div v-if="slotProps.value" class="flex items-center">
                  <span :class="['mr-2', 'text-lg', slotProps.value.icon]"></span>
                </div>
                <span v-else>{{ slotProps.placeholder }}</span>
              </template>

              <!-- Custom dropdown options template -->
              <template #option="slotProps">
                <div class="flex items-center w-1/3">
                  <span :class="['mr-2', 'text-lg', slotProps.option.icon]"></span>
                </div>
              </template>
            </Select>
            <InputText
              class="w-3/7"
              v-model="subTask.taskContent"
              aria-describedby="sub task name"
            />
            <Button
              class="ml-1"
              size="small"
              icon="pi pi-times"
              severity="danger"
              aria-label="Cancel"
              @click="deleteNewSubTask(task.id, subTask.id)"
            />
          </div>
        </div>

        <div class="flex flex-row items-center justify-center">
          <Button
            label="Add sub task"
            icon="pi pi-plus"
            class="w-1/2 mt-2"
            @click="addingNewSubTask(task.id)"
            :disabled="!tasks[task.id - 1]?.taskContent"
          />
        </div>
      </div>

      <div class="flex flex-row items-center justify-center">
        <Button
          label="Add task"
          icon="pi pi-plus"
          class="w-1/2"
          @click="addingNewTask"
          :disabled="!toDoListToAdd.name"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
