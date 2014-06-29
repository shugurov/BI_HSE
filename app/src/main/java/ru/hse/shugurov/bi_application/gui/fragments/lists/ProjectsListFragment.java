package ru.hse.shugurov.bi_application.gui.fragments.lists;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import ru.hse.shugurov.bi_application.gui.adapters.ProjectAdapter;
import ru.hse.shugurov.bi_application.gui.fragments.BaseFragment;
import ru.hse.shugurov.bi_application.gui.fragments.items.ProjectItemFragment;
import ru.hse.shugurov.bi_application.model.Parser;
import ru.hse.shugurov.bi_application.model.ProjectItem;
import ru.hse.shugurov.bi_application.sections.SingleViewSection;

/**
 * Created by Иван on 15.06.2014.
 */
public class ProjectsListFragment extends FragmentWithList
{
    @Override
    protected String getDataUrl()
    {
        return getSection().getUrl();
    }

    @Override
    protected ListAdapter getCurrentAdapter()
    {
        return getSection().getAdapter();
    }

    @Override
    protected ListAdapter getAdapter(String data)
    {
        ProjectItem[] projectItems = Parser.parseProjects(data);
        ListAdapter adapter = new ProjectAdapter(getActivity(), projectItems);
        return adapter;
    }

    @Override
    protected void setSectionAdapter(ListAdapter adapter)
    {
        getSection().setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        ProjectItem projectItem = getSelectedItem(parent, position);
        BaseFragment projectFragment = new ProjectItemFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ProjectItemFragment.PROJECT_ITEM_TAG, projectItem);
        arguments.putSerializable(BaseFragment.SECTION_TAG, getSection());
        projectFragment.setArguments(arguments);
        showNextFragment(projectFragment);
    }

    @Override
    public SingleViewSection getSection()
    {
        return (SingleViewSection) super.getSection();
    }
}
