import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Lucky Yang Admin of Vue'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
