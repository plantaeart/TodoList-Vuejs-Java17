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
import { ElementType } from '@/types/elementType'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const toDoListToAdd = ref(new ToDoList())
const tasks = ref<Task[]>([])
const defaultIcon = new Icon('Star', 'pi pi-star')
const defaultColor = new Color('Yellow 100', 'bg-yellow-100')
const isUpdateState = ref(false)

// To init the state of the form when update
const initUpdateToDoListSate = (toDoListToUpdate: ToDoList, isUpDateState: boolean) => {
  isUpdateState.value = isUpDateState
  toDoListToAdd.value = toDoListToUpdate as ToDoList
  tasks.value = toDoListToUpdate?.tasks as Task[]
}

// Function to reset the state of the form
const resetUpdateToDoListSate = () => {
  isUpdateState.value = false
  toDoListToAdd.value = new ToDoList()
  tasks.value = []
}

// When the component is mounted, set the default icon and color
onMounted(() => {
  addInitForm()
})

const addInitForm = () => {
  toDoListToAdd.value.icon = defaultIcon
  toDoListToAdd.value.color = defaultColor
}

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
    // Remove empty subTasks
    req.toDoLists[0].tasks?.forEach((task) => {
      task.subTasks = task.subTasks.filter((item) => item.taskContent !== '')
    })

    console.log(`Start adding/updating todo list with name : ` + req.toDoLists[0].name)
    if (!isUpdateState.value) {
      // Await the response from the store
      resp = await store.addToDoList(req)
    } else {
      req.idsList = [toDoListToAdd.value.id as number]
      req.toDoLists[0].isCompleted = toDoListToAdd.value.isCompleted
      resp = await store.updateToDoListById(req, ElementType.NONE.toString())

      if (resp.currentResult !== Result.OK) {
        console.error(
          `(BACK) Failed to update ToDoList id : ${toDoListToAdd.value.id}. Response : ${resp.message}`,
        )
      } else {
        const toDoListStore = useToDoListStore()
        const currentToDoList: ToDoList = resp.toDoLists[0]
        // Check if the ToDoList is completed
        toDoListStore.checkToDoListCompletedState(
          currentToDoList.id as number,
          currentToDoList,
          ElementType.TODOLIST.toString(),
          true,
        )
        console.log(`Updated todo list with name : ` + req.toDoLists[0].name)
      }
    }

    // Check if the response is valid and contains the expected data
    if (resp && resp.currentResult === Result.OK) {
      tasks.value.splice(0, tasks.value.length) // Clear the tasks array after successful addition
    } else if (!isUpdateState.value)
      console.error(`Failed to add ToDoList. Response : ${resp.message}`)
    else console.error(`Failed to update ToDoList. Response : ${resp.message}`)
  } catch (error) {
    if (!isUpdateState.value) {
      console.error(`(FRONT) Error while adding ToDoList : ${error}`)
      console.error(`(BACK) Error while adding ToDoList : ${resp.message}`)
    } else {
      console.error(`(FRONT) Error while updating ToDoList : ${error}`)
      console.error(`(BACK) Error while updating ToDoList : ${resp.message}`)
    }
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

// Function to delete a subtask in form
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

// To switch the task up in the list
const switchUpTask = async (idTask: number) => {
  const index = tasks.value.findIndex((task) => task.id === idTask)
  if (index > 0) {
    const temp = tasks.value[index]
    tasks.value[index] = tasks.value[index - 1]
    tasks.value[index - 1] = temp
  }
}

// To switch the task down in the list
const switchDownTask = async (idTask: number) => {
  const index = tasks.value.findIndex((task) => task.id === idTask)
  if (index < tasks.value.length - 1) {
    const temp = tasks.value[index]
    tasks.value[index] = tasks.value[index + 1]
    tasks.value[index + 1] = temp
  }
}

// To switch the subtask up in the list
const switchUpSubTask = async (idTask: number, idSubTask: number) => {
  const indexTask = idTask - 1
  const indexSubTask = tasks.value[indexTask].subTasks.findIndex(
    (subTask) => subTask.id === idSubTask,
  )
  if (indexSubTask > 0) {
    const temp = tasks.value[indexTask].subTasks[indexSubTask]
    tasks.value[indexTask].subTasks[indexSubTask] =
      tasks.value[indexTask].subTasks[indexSubTask - 1]
    tasks.value[indexTask].subTasks[indexSubTask - 1] = temp
  }
}

// To switch the subtask down in the list
const switchDownSubTask = async (idTask: number, idSubTask: number) => {
  const indexTask = idTask - 1
  const indexSubTask = tasks.value[indexTask].subTasks.findIndex(
    (subTask) => subTask.id === idSubTask,
  )
  if (indexSubTask < tasks.value[indexTask].subTasks.length - 1) {
    const temp = tasks.value[indexTask].subTasks[indexSubTask]
    tasks.value[indexTask].subTasks[indexSubTask] =
      tasks.value[indexTask].subTasks[indexSubTask + 1]
    tasks.value[indexTask].subTasks[indexSubTask + 1] = temp
  }
}

const clearDescription = async () => {
  toDoListToAdd.value.description = ''
}

defineExpose({
  addToDoListFromFormInfos,
  initUpdateToDoListSate,
  resetUpdateToDoListSate,
  addInitForm,
})
</script>

<template>
  <div class="flex flex-col items-center m-2 w-3/4">
    <div id="formElements" class="flex flex-col gap-2 w-full justify-center">
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
        <div class="w-full flex flex-row justify-start">
          <Button
            label="Clear"
            class="w-1/5"
            size="small"
            icon="pi pi-trash"
            severity="danger"
            aria-label="Clear"
            raised
            @click="clearDescription()"
          />
        </div>
      </div>

      <div v-for="(task, indexTask) in tasks" :key="task.id">
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
            raised
            @click="deleteNewTask(task.id)"
          />
          <!-- SWITCHIN TASK BEHAVIOR -->
          <div class="flex flex-col items-center justify-center">
            <Button
              :disabled="indexTask === 0"
              size="small"
              icon="pi pi-chevron-up"
              aria-label="Cancel"
              severity="info"
              variant="text"
              rounded
              @click="switchUpTask(task.id)"
            />
            <Button
              :disabled="indexTask === tasks.length - 1"
              size="small"
              icon="pi pi-chevron-down"
              aria-label="Cancel"
              severity="info"
              variant="text"
              rounded
              @click="switchDownTask(task.id)"
            />
          </div>
        </div>
        <div>
          <div
            v-for="(subTask, indexSubTask) in tasks[indexTask].subTasks"
            :key="subTask.id"
            class="flex flex-row justify-end items-center ml-10"
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
              size="small"
              class="ml-1"
              icon="pi pi-times"
              severity="danger"
              aria-label="Cancel"
              raised
              @click="deleteNewSubTask(task.id, subTask.id)"
            />
            <!-- SWITCHIN SUBTASK BEHAVIOR -->
            <div class="flex flex-col items-center justify-center">
              <Button
                :disabled="indexSubTask === 0"
                size="small"
                icon="pi pi-chevron-up"
                aria-label="Switch up"
                severity="help"
                variant="text"
                rounded
                @click="switchUpSubTask(task.id, subTask.id)"
              />
              <Button
                :disabled="indexSubTask === tasks[indexTask].subTasks.length - 1"
                size="small"
                icon="pi pi-chevron-down"
                aria-label="Switch down"
                severity="help"
                variant="text"
                rounded
                @click="switchDownSubTask(task.id, subTask.id)"
              />
            </div>
          </div>
        </div>

        <div class="flex flex-row items-center justify-center">
          <Button
            label="Add sub task"
            icon="pi pi-plus"
            class="w-1/2 mt-2"
            severity="help"
            raised
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
          severity="info"
          raised
          @click="addingNewTask"
          :disabled="!toDoListToAdd.name"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
