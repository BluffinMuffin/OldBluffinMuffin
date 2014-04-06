namespace Com.Ericmas001.Game.BluffinMuffin.GUI.Lobby
{
    partial class CreateTableForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CreateTableForm));
            this.tabControl1 = new EricUtility.Windows.Forms.CustomTabControl();
            this.btnAddTable = new VIBlend.WinForms.Controls.vButton();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.tabControl1.DisplayStyle = EricUtility.Windows.Forms.TabStyle.Rounded;
            // 
            // 
            // 
            this.tabControl1.DisplayStyleProvider.BorderColor = System.Drawing.SystemColors.ControlDark;
            this.tabControl1.DisplayStyleProvider.BorderColorHot = System.Drawing.SystemColors.ControlDark;
            this.tabControl1.DisplayStyleProvider.BorderColorSelected = System.Drawing.Color.FromArgb(((int)(((byte)(127)))), ((int)(((byte)(157)))), ((int)(((byte)(185)))));
            this.tabControl1.DisplayStyleProvider.CloserColor = System.Drawing.Color.DarkGray;
            this.tabControl1.DisplayStyleProvider.FocusTrack = false;
            this.tabControl1.DisplayStyleProvider.HotTrack = true;
            this.tabControl1.DisplayStyleProvider.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.tabControl1.DisplayStyleProvider.Opacity = 1F;
            this.tabControl1.DisplayStyleProvider.Overlap = 0;
            this.tabControl1.DisplayStyleProvider.Padding = new System.Drawing.Point(6, 3);
            this.tabControl1.DisplayStyleProvider.Radius = 10;
            this.tabControl1.DisplayStyleProvider.ShowTabCloser = false;
            this.tabControl1.DisplayStyleProvider.TextColor = System.Drawing.SystemColors.ControlText;
            this.tabControl1.DisplayStyleProvider.TextColorDisabled = System.Drawing.SystemColors.ControlDark;
            this.tabControl1.DisplayStyleProvider.TextColorSelected = System.Drawing.SystemColors.ControlText;
            this.tabControl1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tabControl1.HotTrack = true;
            this.tabControl1.Location = new System.Drawing.Point(12, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(480, 308);
            this.tabControl1.TabIndex = 0;
            // 
            // btnAddTable
            // 
            this.btnAddTable.AllowAnimations = true;
            this.btnAddTable.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.btnAddTable.BackColor = System.Drawing.Color.Transparent;
            this.btnAddTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnAddTable.Image = global::Com.Ericmas001.Game.BluffinMuffin.GUI.Properties.Resources.cards;
            this.btnAddTable.Location = new System.Drawing.Point(12, 326);
            this.btnAddTable.Name = "btnAddTable";
            this.btnAddTable.RoundedCornersMask = ((byte)(15));
            this.btnAddTable.Size = new System.Drawing.Size(480, 53);
            this.btnAddTable.TabIndex = 16;
            this.btnAddTable.Text = "Create Table";
            this.btnAddTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnAddTable.UseVisualStyleBackColor = true;
            this.btnAddTable.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnAddTable.Click += new System.EventHandler(this.btnAddTable_Click);
            // 
            // CreateTableForm
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
            this.ClientSize = new System.Drawing.Size(504, 391);
            this.Controls.Add(this.btnAddTable);
            this.Controls.Add(this.tabControl1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimumSize = new System.Drawing.Size(520, 430);
            this.Name = "CreateTableForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Create New Table";
            this.ResumeLayout(false);

        }

        #endregion

        private EricUtility.Windows.Forms.CustomTabControl tabControl1;
        private VIBlend.WinForms.Controls.vButton btnAddTable;
    }
}