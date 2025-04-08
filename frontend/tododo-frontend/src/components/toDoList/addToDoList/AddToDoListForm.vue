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

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const toDoListToAdd = ref(new ToDoList())
const tasks = ref<Task[]>([])
const selectedColor = ref(new Color())
const selectedIcons = ref(new Array<Icon>())
const defaultIcon = new Icon('Star', 'pi pi-star')
const defaultColor = new Color('Yellow 100', 'bg-yellow-100')

onMounted(() => {
  selectedIcons.value.push(defaultIcon)
  selectedColor.value = defaultColor
})

const addToDoListFromFormInfos = async () => {
  let resp: ToDoListResponse = new ToDoListResponse()
  // Adding the icon to correct task
  try {
    tasks.value.forEach((task) => {
      task.icon = new Icon(selectedIcons.value[task.id].name, selectedIcons.value[task.id].icon)
    })

    req.toDoLists = [
      {
        name: toDoListToAdd.value.name,
        description: toDoListToAdd.value.description,
        icon: new Icon(selectedIcons.value[0].name, selectedIcons.value[0].icon),
        color: new Color(selectedColor.value.name, selectedColor.value.color),
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
  const idTask = tasks.value.length + 1
  tasks.value.push(new Task({ id: idTask, taskContent: '', icon: defaultIcon }))
  selectedIcons.value[idTask] = defaultIcon
}

// Function to delete a task in form
const deleteNewTask = async (taskId: number) => {
  if (!toDoListToAdd.value.name) return

  const index = tasks.value.findIndex((task) => task.id === taskId)
  if (index !== -1) {
    tasks.value.splice(index, 1)
  }

  tasks.value = rearrangeArrayIds(tasks.value)
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
        <Select
          v-model="selectedColor"
          :options="colors"
          filter
          optionLabel="name"
          class="w-1/4 mr-4 size-10"
          size="large"
          @value-change="
            (selectedColor) => {
              selectedColor.value = selectedColor as Color
            }
          "
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
        <Select
          v-model="selectedIcons[0]"
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

      <div
        v-for="(task, index) in tasks"
        :key="task.id"
        class="flex flex-row justify-end items-center"
      >
        <Select
          v-model="selectedIcons[index + 1]"
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
